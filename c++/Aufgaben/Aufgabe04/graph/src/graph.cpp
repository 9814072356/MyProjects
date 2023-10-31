/**
 * @file graph.cpp
 */
#include "graph.h"
#include <algorithm>

// Complete the following constructor:
Vertex::Vertex(std::string name, size_t vertexId)
{
    this->name = name;
    this->id = vertexId;
}

/**
     * Adds an Edge pointing towards this Vertex
     * @param id ID of the Edge pointing towards this Vertex
     */
void Vertex::addInEdgeId(size_t edgeId)
{
    // Add code here.
    this->inEdgeIds.emplace_back(edgeId);
}

/**
    * Adds an Edge which points out of this Vertex
    * @param id ID of an Edge which points out of this Vertex
    */
void Vertex::addOutEdgeId(size_t edgeId)
{
    // Add code here.
    this->outEdgeIds.emplace_back(edgeId);
}

// Complete the following constructor:
Edge::Edge(size_t edgeId, size_t inVertexId, size_t outVertexId)
{
    this->id = edgeId;

    /** ID of the Vertex connected to the tail of the Edge */
    this->inVertex = inVertexId;

    /** ID of the Vertex connected to the head of the Edge */
    this->outVertex = outVertexId;
}

/**
 * Factory function to make a new Vertex
 * @returns Id of the new Vertex
 */
int vertexIDs = 0;
size_t Graph::makeVertex(const std::string &name)
{
    // Add code here.
    Vertex temp(name, vertexIDs);
    vertexIDs++;
    this->vertices.emplace_back(temp);
    return temp.getId();
}

/**
 * Factory function to make a new Edge
 * @returns Id of the new edge
 */
int edgeIDs = 0;
size_t Graph::makeEdge(const size_t inVertexId, const size_t outVertexId)
{
    // Add code here.
    // Do not forget to set in and out edge IDs to the vertices.

    Edge temp(edgeIDs, inVertexId, outVertexId);
    this->edges.emplace_back(temp);
    this->vertices[outVertexId].addInEdgeId(edgeIDs);
    this->vertices[inVertexId].addOutEdgeId(edgeIDs);
    edgeIDs++;
    return temp.getId();
}

Vertex &Graph::getVertex(size_t id) { return this->vertices[id]; }
Edge &Graph::getEdge(size_t id) { return this->edges[id]; }

const Vertex &Graph::getVertex(size_t id) const { return this->vertices[id]; }
const Edge &Graph::getEdge(size_t id) const { return this->edges[id]; }

void printGraph(std::ostream &os, const Graph &graph)
{
    os << "-------------------------------------------" << std::endl;
    for (const Vertex &vertex : graph.getVertices())
    {
        os << "Vertex Name: " << vertex.getName() << std::endl;
        os << "Input Edges: " << std::endl;
        if (vertex.getInEdgeIds().empty())
        {
            os << " " << std::endl;
        }
        else
        {
            for (const size_t &inEdgeId : vertex.getInEdgeIds())
            {
                os << "Edge ID: " << inEdgeId << std::endl;
            }
        }
        os << "Output Edges: " << std::endl;
        if (vertex.getOutEdgeIds().empty())
        {
            os << " " << std::endl;
        }
        else
        {
            for (const size_t &outEdgeId : vertex.getOutEdgeIds())
            {
                os << "Edge ID: " << outEdgeId << std::endl;
            }
        }
        os << "\n";
    }
    os << "-------------------------------------------" << std::endl;
}
