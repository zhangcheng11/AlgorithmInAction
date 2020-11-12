package com.atstone.algorithm.stucture.graph;

public interface Graph<V,E> {
    /**
     * 边的总数
     */
     int edgesSize();

    /**
     * 顶点总数
     */
     int verticesSize();

    /**
     *  添加顶点
     */
     void addVertex(V v);

    /**
     * 添加边
     * @param from 边的起点
     * @param to   边的终点
     */
     void addEdge(V from, V to);

    /**
     * 添加加权的边
     * @param from 边的起点
     * @param to   边的终点
     * @param weight 权值
     */
     void addEdge(V from, V to, E weight);

    /**
     * 移除顶点
     * @param v
     */
     void removeVertex(V v);

    /**
     * 移除边
     * @param from  边的起点
     * @param to    边的终点
     */
     void removeEdge(V from, V to);

    /**
     * 广度优先搜索
     * @param begin  搜索的起点
     */
     void bfs(V begin,VertexVisitor<V> visitor);
    /**
     * 深度优先搜索
     * @param begin  搜索的起点
     */
    void dfs(V begin,VertexVisitor<V> visitor);

    interface VertexVisitor<V>{
        boolean visit(V value);
    }
}
