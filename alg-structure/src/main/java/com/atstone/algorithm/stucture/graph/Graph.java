package com.atstone.algorithm.stucture.graph;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Graph<V, E> {
    /**
     * 边的总数
     */
    int edgesSize();

    /**
     * 顶点总数
     */
    int verticesSize();

    /**
     * 添加顶点
     */
    void addVertex(V v);

    /**
     * 添加边
     *
     * @param from 边的起点
     * @param to   边的终点
     */
    void addEdge(V from, V to);

    /**
     * 添加加权的边
     *
     * @param from   边的起点
     * @param to     边的终点
     * @param weight 权值
     */
    void addEdge(V from, V to, E weight);

    /**
     * 移除顶点
     *
     * @param v
     */
    void removeVertex(V v);

    /**
     * 移除边
     *
     * @param from 边的起点
     * @param to   边的终点
     */
    void removeEdge(V from, V to);

    /**
     * 广度优先搜索
     *
     * @param begin 搜索的起点
     */
    void bfs(V begin, VertexVisitor<V> visitor);

    /**
     * 深度优先搜索
     *
     * @param begin 搜索的起点
     */
    void dfs(V begin, VertexVisitor<V> visitor);

    /**
     * 拓扑排序
     *
     * @return
     */
    List<V> topologicalSort();

    /**
     * 最小生成树
     *
     * @return 最小生成树的边信息集合
     */
    Set<EdgeInfo<V, E>> mst();


    Map<V, E> shortestPath(V begin);

    interface VertexVisitor<V> {
        boolean visit(V value);
    }

    class EdgeInfo<V, E> {
        V from;
        V to;
        E weight;

        public EdgeInfo(V from, V to, E weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public V getFrom() {
            return from;
        }

        public void setFrom(V from) {
            this.from = from;
        }

        public V getTo() {
            return to;
        }

        public void setTo(V to) {
            this.to = to;
        }

        public E getWeight() {
            return weight;
        }

        public void setWeight(E weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "EdgeInfo [from=" + from + ", to=" + to + ", weight=" + weight + "]";
        }
    }
}
