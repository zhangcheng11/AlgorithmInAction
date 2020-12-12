package com.atstone.algorithm.stucture.graph;


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
    private WeightManager<E> weightManager;

    private Comparator<Edge<V, E>> edgeComparator = (Edge<V, E> e1, Edge<V, E> e2) -> {
        return weightManager.compare(e1.weight, e2.weight);
    };

    public ListGraph() {
    }

    public ListGraph(WeightManager<E> weightManager) {
        this.weightManager = weightManager;
    }

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
        return Math.random() > 0.5 ? prim() : kruskal();
    }

    @Override
    public Map<V, E> shortestPath(V begin) {
        Vertex<V, E> beginVertex = vertexes.get(begin);
        if (beginVertex == null) return null;
        Map<V, E> selectedPaths = new HashMap<>();
        Map<Vertex<V, E>, E> paths = new HashMap<>();
        //初始化
        for (Edge<V, E> edge : beginVertex.outEdges) {
            paths.put(edge.to, edge.weight);
        }

        while (!paths.isEmpty()) {
            Map.Entry<Vertex<V, E>, E> minEntry = getMinPath(paths);
            //minVeVertex离开桌面，
            Vertex<V, E> minVeVertex = minEntry.getKey();
            selectedPaths.put(minVeVertex.value, minEntry.getValue());
            paths.remove(minVeVertex);
            //对minVeVertex它的outEdges进行松弛操作
            for (Edge<V, E> edge : minVeVertex.outEdges) {
                //if(selectedPaths.containsKey(edge.to.value) || edge.to.equals(beginVertex)) continue;
                if(selectedPaths.containsKey(edge.to.value) || edge.to.equals(beginVertex)) continue;
                //以前的最短路径：beginVertex到edge.to的最短路径
                //新的可选择的最短路径：beginVertex到edge.from 的最短路径 +  edge.weight
                E newWeight = weightManager.add(minEntry.getValue(), edge.weight);
                E oldWeight = paths.get(edge.to);
                if (oldWeight == null || weightManager.compare(newWeight, oldWeight) < 0) {
                    paths.put(edge.to, newWeight);
                }
            }
        }
        selectedPaths.remove(begin);
        return selectedPaths;
    }

    private void relax() {

    }

    private Map.Entry<Vertex<V, E>, E> getMinPath(Map<Vertex<V, E>, E> paths) {
        Iterator<Map.Entry<Vertex<V, E>, E>> iterator = paths.entrySet().iterator();
        Map.Entry<Vertex<V, E>, E> minEntry = iterator.next();
        while (iterator.hasNext()) {
            Map.Entry<Vertex<V, E>, E> entry = iterator.next();
            if (weightManager.compare(entry.getValue(), minEntry.getValue()) < 0) {
                minEntry = entry;
            }
        }
        return minEntry;
    }

    /**
     * 采用prim算法实现最小生成树
     *
     * @return 最小生成树的边信息结合
     */
    private Set<EdgeInfo<V, E>> prim() {
        Iterator<Vertex<V, E>> iterator = vertexes.values().iterator();
        if (!iterator.hasNext()) return null;
        Vertex<V, E> vertex = iterator.next();

        Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();
        Set<Vertex<V, E>> addedVertexes = new HashSet<>();
        addedVertexes.add(vertex);
        int vertexSize = vertexes.size();
        MinHeap<Edge<V, E>> edgeMinHeap = new MinHeap<>(vertex.outEdges, edgeComparator);
        while (!edgeMinHeap.isEmpty() && addedVertexes.size() < vertexSize) {
            Edge<V, E> edge = edgeMinHeap.remove();
            if (addedVertexes.contains(edge.to)) continue;
            edgeInfos.add(edge.info());
            addedVertexes.add(edge.to);
            edgeMinHeap.addAll(edge.to.outEdges);
        }
        return edgeInfos;
    }

    private Set<EdgeInfo<V, E>> kruskal() {
        int edgeSize = vertexes.size() - 1;
        if (edgeSize == -1) return null;
        Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();
        MinHeap<Edge<V, E>> edgeMinHeap = new MinHeap<>(edges, edgeComparator);
        UnionFind<Vertex<V, E>> uf = new UnionFind<>();
        vertexes.forEach((V v, Vertex<V, E> vertex) -> {
            uf.makeSet(vertex);
        });
        while (!edgeMinHeap.isEmpty() && edgeInfos.size() < edgeSize) {
            Edge<V, E> edge = edgeMinHeap.remove();
            if (uf.isSame(edge.from, edge.to)) continue;
            edgeInfos.add(edge.info());
            uf.union(edge.from, edge.to);
        }
        return edgeInfos;
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

    public interface WeightManager<E> {
        int compare(E w1, E w2);

        E add(E w1, E w2);

        E zero();
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

        EdgeInfo<V, E> info() {
            return new EdgeInfo<>(from.value, to.value, weight);
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
