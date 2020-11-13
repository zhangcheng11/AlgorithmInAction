package com.atstone.algorithm.stucture.graph;

import com.atstone.algorithm.stucture.heap.BinaryHeap;

import java.util.*;

/**
 * 图：基于邻接表的实现
 *
 * @param <V>
 * @param <E>
 */
public class ListGraph<V, E> implements Graph<V, E> {
    private Map<V, Vertex<V, E>> vertexes = new HashMap<>();
    private Set<Edge<V, E>> edges = new HashSet<>();

    @Override
    public int edgesSize() {
        return edges.size();
    }

    @Override
    public int verticesSize() {
        return vertexes.size();
    }

    @Override
    public void addVertex(V v) {
        if (vertexes.containsKey(v)) return;
        vertexes.put(v, new Vertex<>(v));
    }

    @Override
    public void addEdge(V from, V to) {
        addEdge(from, to, null);
    }

    @Override
    public void addEdge(V from, V to, E weight) {
        Vertex<V, E> fromVertex = vertexes.get(from);
        if (fromVertex == null) {
            fromVertex = new Vertex<>(from);
            vertexes.put(from, fromVertex);
        }

        Vertex<V, E> toVertex = vertexes.get(to);
        if (toVertex == null) {
            toVertex = new Vertex<>(to);
            vertexes.put(to, toVertex);
        }

        Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
        edge.weight = weight;

        if (fromVertex.outEdges.remove(edge)) {
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }
        fromVertex.outEdges.add(edge);
        toVertex.inEdges.add(edge);
        edges.add(edge);
    }

    @Override
    public void removeVertex(V v) {
        Vertex<V, E> vertex = vertexes.remove(v);
        if (vertex == null) return;

        //与v相关的边也要删掉
        Iterator<Edge<V, E>> iterator = vertex.outEdges.iterator();
        while (iterator.hasNext()) {
            Edge<V, E> edge = iterator.next();
            edge.to.inEdges.remove(edge);//该边也在终点的inEdges中，需要掉
            iterator.remove();//该边在该顶点的outEdges中，也就是正在遍历的边
            edges.remove(edge);
        }

        iterator = vertex.inEdges.iterator();
        while (iterator.hasNext()) {
            Edge<V, E> edge = iterator.next();
            edge.from.outEdges.remove(edge);//该边也在起点的outEdges中，需要掉
            iterator.remove();//该边在该顶点的inEdges中，也就是正在遍历的边
            edges.remove(edge);
        }

    }

    @Override
    public void removeEdge(V from, V to) {
        Vertex<V, E> fromVertex = vertexes.get(from);
        if (fromVertex == null) return;
        Vertex<V, E> toVertex = vertexes.get(to);
        if (toVertex == null) return;

        Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
        if (fromVertex.outEdges.remove(edge)) {
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }
    }

    /**
     * 广度优先搜索的实现与二分搜索树中的层序遍历一样的思路
     *
     * @param begin 搜索的起点
     */
    @Override
    public void bfs(V begin, VertexVisitor<V> visitor) {
        if (visitor == null) return;
        Vertex<V, E> beginVertex = vertexes.get(begin);
        if (beginVertex == null) return;

        Set<Vertex<V, E>> visitedVertexes = new HashSet<>();
        Queue<Vertex<V, E>> queue = new LinkedList<>();
        queue.offer(beginVertex);
        visitedVertexes.add(beginVertex);

        while (!queue.isEmpty()) {
            Vertex<V, E> vertex = queue.poll();
            //System.out.println(vertex);
            if (visitor.visit(vertex.value)) break;
            for (Edge<V, E> edge : vertex.outEdges) {
                if (visitedVertexes.contains(edge.to)) continue;
                queue.offer(edge.to);
                visitedVertexes.add(edge.to);
            }
        }
    }

    /**
     * 深度优先搜索的实现与二分搜索树中的前序遍历一样的思路
     *
     * @param begin 搜索的起点
     */
    @Override
    public void dfs(V begin, VertexVisitor<V> visitor) {
        if (visitor == null) return;
        Vertex<V, E> beginVertex = vertexes.get(begin);
        if (beginVertex == null) return;

        Set<Vertex<V, E>> visitedVertexes = new HashSet<>();

        dfs(beginVertex, visitedVertexes, visitor);
    }

    @Override
    public List<V> topologicalSort() {
        List<V> list = new ArrayList<>();
        Queue<Vertex<V, E>> queue = new LinkedList<>();
        Map<Vertex<V, E>, Integer> ins = new HashMap<>();
        // 初始化（将度为0的节点都放入队列）
        vertexes.forEach((V v, Vertex<V, E> vertex) -> {
            int inDegree = vertex.inEdges.size();
            if (inDegree == 0) {
                queue.offer(vertex);
            } else {
                ins.put(vertex, inDegree);
            }
        });

        while (!queue.isEmpty()) {
            Vertex<V, E> vertex = queue.poll();
            // 放入返回结果中
            list.add(vertex.value);
            for (Edge<V, E> edge : vertex.outEdges) {
                int toInDegree = ins.get(edge.to) - 1;
                if (toInDegree == 0) {
                    queue.offer(edge.to);
                } else {
                    ins.put(edge.to, toInDegree);
                }
            }
        }
        return list;
    }

    @Override
    public Set<EdgeInfo<V, E>> mst() {
        return prim();
    }

    /**
     * 采用prim算法实现最小生成树
     * @return  最小生成树的边信息结合
     */
    private Set<EdgeInfo<V, E>> prim() {
        Iterator<Vertex<V, E>> iterator = vertexes.values().iterator();
        if(!iterator.hasNext()) return null;
        Vertex<V, E> vertex = iterator.next();

        Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();
        Set<Vertex<V, E>> addedVertexes = new HashSet<>();
        addedVertexes.add(vertex);
        int vertexSize = vertexes.size();


        return null;
    }

    private Set<EdgeInfo<V, E>> kruskal() {
        return null;
    }

    private void dfs(Vertex<V, E> vertex, Set<Vertex<V, E>> visitedVertexes, VertexVisitor<V> visitor) {
        //System.out.println(vertex);
        if (visitor.visit(vertex.value)) return;
        visitedVertexes.add(vertex);

        for (Edge<V, E> edge : vertex.outEdges) {
            if (visitedVertexes.contains(edge.to)) continue;
            dfs(edge.to, visitedVertexes, visitor);
        }
    }

    /**
     * 深度优先搜索的非递归的实现
     *
     * @param begin 搜索的起点
     */
    public void dfs2(V begin, VertexVisitor<V> visitor) {
        if (visitor == null) return;
        Vertex<V, E> beginVertex = vertexes.get(begin);
        if (beginVertex == null) return;

        Set<Vertex<V, E>> visitedVertexes = new HashSet<>();

        Stack<Vertex<V, E>> stack = new Stack<>();
        stack.push(beginVertex);
        visitedVertexes.add(beginVertex);
        //System.out.println(beginVertex.value);
        if (visitor.visit(beginVertex.value)) return;

        while (!stack.isEmpty()) {
            Vertex<V, E> vertex = stack.pop();
            //System.out.println(vertex);

            for (Edge<V, E> edge : vertex.outEdges) {
                if (visitedVertexes.contains(edge.to)) continue;
                stack.push(edge.from);
                stack.push(edge.to);
                visitedVertexes.add(edge.to);
                //System.out.println(edge.to.value);
                if (visitor.visit(edge.to.value)) return;
                break;
            }
        }
    }

    private static class Vertex<V, E> {
        V value;
        Set<Edge<V, E>> inEdges = new HashSet<>();
        Set<Edge<V, E>> outEdges = new HashSet<>();

        public Vertex(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            return Objects.equals(value, ((Vertex<V, E>) obj).value);
        }

        @Override
        public int hashCode() {
            return value == null ? 0 : value.hashCode();
        }

        @Override
        public String toString() {
            return value == null ? "null" : value.toString();
        }
    }

    private static class Edge<V, E> {
        Vertex<V, E> from;//起点
        Vertex<V, E> to;//终点
        E weight;//权值

        public Edge(Vertex<V, E> from, Vertex<V, E> to) {
            this.from = from;
            this.to = to;
        }

        public Edge(Vertex<V, E> from, Vertex<V, E> to, E weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object obj) {
            Edge<V, E> edge = (Edge<V, E>) obj;
            return Objects.equals(this.from, edge.from) && Objects.equals(this.to, edge.to);
        }

        @Override
        public int hashCode() {
            return from.hashCode() * 31 + to.hashCode();
        }

        @Override
        public String toString() {
            return "Edge [from=" + from + ", to=" + to + ", weight=" + weight + "]";
        }
    }
}
