#include <cmath>
#include <tuple>
#include <iostream>
#include "graph.h"

std::ostream &operator<<(std::ostream &os, const Graph &graph)
{
    os << "Nodes : \n";
    graph.forAllNodes([&](std::size_t nodeId) {
        os << nodeId << '\n';
    });
    os << "Edges : \n";
    graph.forAllEdges([&](std::size_t edgeId) {
        auto [from, to] = graph.getAdjacentNodeIds(edgeId);
        os << edgeId << " : " << from << "->( " << graph.getEdgeWeight(edgeId) << " )->" << to << '\n';
    });
    os.flush();
    return os;
}

// TODO: Implement member functions of AdjacentGraph and ClassicGraph
//--------------------------------------------------------------------------------------------
AdjacentGraph::AdjacentGraph() : edgeMatrix(), nodes() {}

/// Inserts a directed Edge into the Graph if the Ids of both Nodes are valid within the Graph
/// @param nodeId1 Id of incoming Node from which the Edge originates
/// @param nodeId2 Id of outgoing Node to which the Edge leads
/// @param weight Weight of the Edge
/// @returns Id of the new Edge
// check ob ids < size();
// 2 for loops bis ids, weight einfügen
// return place counter im array 0-> size()*size()
std::size_t AdjacentGraph::addEdge(std::size_t nodeId1, std::size_t nodeId2, double weight)
{
    int indexCounter = 0;
    if (nodeId1 < this->nodes.size() && nodeId2 < this->nodes.size())
    {
        for (long unsigned int i = 0; i < this->nodes.size(); i++)
        {
            for (long unsigned int j = 0; j < this->nodes.size(); j++)
            {
                if (i == nodeId1 && j == nodeId2)
                {
                    this->edgeMatrix[i][j] = weight;
                }
                indexCounter++;
            }
        }
    }
    return indexCounter;
}
/// Adds a new Node to the Graph and returns its Id
/// @returns Id of the new Node
// in node vector den Wert emplacen und den Index zurückgeben
std::size_t AdjacentGraph::addNode()
{
    size_t nodeCounter = this->nodes.size();
    this->nodes.emplace_back(nodeCounter);

    this->edgeMatrix.clear();
    this->edgeMatrix.resize(this->nodes.size(), std::vector<double>(this->nodes.size()));

    return nodeCounter;
}

/// Returns a tuple of the two Nodes connected by an Edge
/// @param edgeId Id of the Edge
/// @returns Tuple of the Ids of the two Nodes connected by the given Edge
// über 2d vector itarieren bis index = edgeID und i und j als tuple zurück geben
std::tuple<std::size_t, std::size_t> AdjacentGraph::getAdjacentNodeIds(std::size_t edgeId) const
{
    size_t indexCounter = 0;
    std::tuple<std::size_t, std::size_t> result(0, 0);

    for (long unsigned int i = 0; i < this->nodes.size(); i++)
    {
        for (long unsigned int j = 0; j < this->nodes.size(); j++)
        {
            if (indexCounter == edgeId)
            {
                result = std::tuple<std::size_t, std::size_t>(i, j);
            }
            indexCounter++;
        }
    }
    return result;
}

/// Returns Ids of all Edges going into a Node
/// @param nodeId Id of the Node
/// @returns Vector of Ids of incoming Edges
// über 2d vektor iterieren bis nodeId == j und entsprechenden counter der mitläuft in vektor packen und returnen
std::vector<std::size_t> AdjacentGraph::getInEdgeIds(std::size_t nodeId) const
{
    size_t indexCounter = 0;
    std::vector<std::size_t> result;

    for (long unsigned int i = 0; i < this->nodes.size(); i++)
    {
        for (long unsigned int j = 0; j < this->nodes.size(); j++)
        {
            if (j == nodeId)
            {
                result.emplace_back(indexCounter);
            }
            indexCounter++;
        }
    }
    return result;
}

/// Returns Ids of all Edges coming out of a Node
/// @param nodeId Id of the Node
/// @returns Vector of Ids of outgoing Edges
// über 2d vektor iterieren bis nodeId == i und entsprechenden counter der mitläuft in vektor packen und returnen
std::vector<std::size_t> AdjacentGraph::getOutEdgeIds(std::size_t nodeId) const
{
    size_t indexCounter = 0;
    std::vector<std::size_t> result;

    for (long unsigned int i = 0; i < this->nodes.size(); i++)
    {
        for (long unsigned int j = 0; j < this->nodes.size(); j++)
        {
            if (i == nodeId)
            {
                result.emplace_back(indexCounter);
            }
            indexCounter++;
        }
    }
    return result;
}

/// Replaces the weight of an Edge
/// @param edgeId Id of the Edge
/// @param weight New weight to be se
// über 2d vektor iterarieren wenn counter = edgeID .at() = weight
void AdjacentGraph::setEdgeWeight(std::size_t edgeId, double weight)
{
    size_t indexCounter = 0;
    for (long unsigned int i = 0; i < this->nodes.size(); i++)
    {
        for (long unsigned int j = 0; j < this->nodes.size(); j++)
        {
            if (indexCounter == edgeId)
            {
                this->edgeMatrix[i][j] = weight;
            }
            indexCounter++;
        }
    }
}

/// Returns the weight of an Edge
/// @param edgeId Id of the Edge
// über 2d vektor iterarieren wenn counter = edgeID return .at()
double AdjacentGraph::getEdgeWeight(std::size_t edgeId) const
{
    size_t indexCounter = 0;
    double result = -1;
    for (long unsigned int i = 0; i < this->nodes.size(); i++)
    {
        for (long unsigned int j = 0; j < this->nodes.size(); j++)
        {
            if (indexCounter == edgeId)
            {
                result = this->edgeMatrix[i][j];
            }
            indexCounter++;
        }
    }
    return result;
}

/// Executes a function on all Edges of the Graph
/// @param function The function to be called
void AdjacentGraph::forAllEdges(const std::function<void(std::size_t edgeId)> &function) const
{
    size_t indexCounter = 0;
    for (long unsigned int i = 0; i < this->nodes.size(); i++)
    {
        for (long unsigned int j = 0; j < this->nodes.size(); j++)
        {
            if (this->edgeMatrix[i][j] > 0)
            {
                function(indexCounter);
            }
            indexCounter++;
        }
    }
}

/// Executes a function on all Nodes of the Graph
/// @param function The function to be called
void AdjacentGraph::forAllNodes(const std::function<void(std::size_t nodeId)> &function) const
{
    for (long unsigned int i = 0; i < this->nodes.size(); i++)
    {
        function(this->nodes.at(i));
    }
}

//--------------------------------------------------------------------------------------------
ClassicGraph::ClassicGraph() {}

std::size_t ClassicGraph::addEdge(std::size_t nodeId1, std::size_t nodeId2, double weight)
{
    size_t result = 0;
    if (nodeId1 < this->nodes.size() && nodeId2 < this->nodes.size())
    {
        ClassicGraph::Edge current;
        current.setNodeId1(nodeId1);
        current.setNodeId2(nodeId2);
        current.setWeight(weight);
        this->edges.emplace_back(current);
        // this->nodes.emplace_back().emplaceBackIncoming(nodeId1);
        // this->nodes.emplace_back().emplaceBackOutgoing(nodeId2);
        result = this->edges.size() - 1;
    }
    return result;
}

std::size_t ClassicGraph::addNode()
{
    this->nodes.resize(this->nodes.size() + 1);
    return this->nodes.size();
}

std::tuple<std::size_t, std::size_t> ClassicGraph::getAdjacentNodeIds(std::size_t edgeId) const
{
    std::tuple<std::size_t, std::size_t> result(0, 0);
    for (long unsigned i = 0; i < this->edges.size(); i++)
    {
        if (edgeId == i)
        {
            result = std::tuple<std::size_t, std::size_t>(this->edges.at(i).getNodeId1(), this->edges.at(i).getNodeId2());
        }
    }
    return result;
}

std::vector<std::size_t> ClassicGraph::getInEdgeIds(std::size_t nodeId) const
{
    std::vector<std::size_t> result;
    for (long unsigned i = 0; i < this->nodes.size(); i++)
    {
        if (nodeId == i)
        {
            result.emplace_back(this->nodes.at(i).getIncomingEdges().at(i));
        }
    }
    return result;
}
std::vector<std::size_t> ClassicGraph::getOutEdgeIds(std::size_t nodeId) const
{
    std::vector<std::size_t> result;
    for (long unsigned i = 0; i < this->nodes.size(); i++)
    {
        if (nodeId == i)
        {
            result.emplace_back(this->nodes.at(i).getOutgoingEdges().at(i));
        }
    }
    return result;
}

void ClassicGraph::setEdgeWeight(std::size_t edgeId, double weight)
{
    for (long unsigned i = 0; i < this->edges.size(); i++)
    {
        if (edgeId == i)
        {
            this->edges.at(i).setWeight(weight);
        }
    }
}
double ClassicGraph::getEdgeWeight(std::size_t edgeId) const
{
    double result;
    for (long unsigned i = 0; i < this->edges.size(); i++)
    {
        if (edgeId == i)
        {
            result = this->edges.at(i).getWeight();
        }
    }
    return result;
}
void ClassicGraph::forAllEdges(const std::function<void(std::size_t edgeId)> &function) const
{
    for (long unsigned i = 0; i < this->edges.size(); i++)
    {
        function(i);
    }
}
void ClassicGraph::forAllNodes(const std::function<void(std::size_t nodeId)> &function) const
{
    for (long unsigned int i = 0; i < this->nodes.size(); i++)
    {
        function(i);
    }
}