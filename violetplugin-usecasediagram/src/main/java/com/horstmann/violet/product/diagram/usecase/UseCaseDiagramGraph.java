/*
 Violet - A program for editing UML diagrams.

 Copyright (C) 2007 Cay S. Horstmann (http://horstmann.com)
 Alexandre de Pellegrin (http://alexdp.free.fr);

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.horstmann.violet.product.diagram.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.horstmann.violet.product.diagram.abstracts.AbstractGraph;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.common.edge.NoteEdge;
import com.horstmann.violet.product.diagram.common.node.NoteNode;
import com.horstmann.violet.product.diagram.usecase.edge.UseCaseRelationshipEdge;
import com.horstmann.violet.product.diagram.usecase.node.ActorNode;
import com.horstmann.violet.product.diagram.usecase.node.UseCaseNode;

/**
 * A UML use case diagram.
 */
public class UseCaseDiagramGraph extends AbstractGraph
{

    public List<INode> getNodePrototypes()
    {
        return NODE_PROTOTYPES;
    }

    public List<IEdge> getEdgePrototypes()
    {
        return EDGE_PROTOTYPES;
    }

    private static final List<INode> NODE_PROTOTYPES = new ArrayList<INode>();

    private static final List<IEdge> EDGE_PROTOTYPES = new ArrayList<IEdge>();

    static
    {
        ResourceBundle rs = ResourceBundle.getBundle(UseCaseDiagramConstant.USECASE_DIAGRAM_STRINGS, Locale.getDefault());

        ActorNode actorNode = new ActorNode();
        actorNode.setToolTip(rs.getString("node0.tooltip"));
        NODE_PROTOTYPES.add(actorNode);

        UseCaseNode useCaseNode = new UseCaseNode();
        useCaseNode.setToolTip(rs.getString("node1.tooltip"));
        NODE_PROTOTYPES.add(useCaseNode);

        NoteNode noteNode = new NoteNode();
        noteNode.setToolTip(rs.getString("node2.tooltip"));
        NODE_PROTOTYPES.add(noteNode);

        UseCaseRelationshipEdge communication = new UseCaseRelationshipEdge();
//        communication.setBentStyle(BentStyleChoiceList.STRAIGHT);
//        communication.setLineStyle(LineStyleChoiceList.SOLID);
//        communication.setEndArrowHead(ArrowheadChoiceList.NONE);
        communication.setToolTip(rs.getString("edge0.tooltip"));
        EDGE_PROTOTYPES.add(communication);

        UseCaseRelationshipEdge extendRel = new UseCaseRelationshipEdge();
//        extendRel.setBentStyle(BentStyleChoiceList.STRAIGHT);
//        extendRel.setLineStyle(LineStyleChoiceList.DOTTED);
//        extendRel.setEndArrowHead(ArrowheadChoiceList.V);
        extendRel.setCenterLabel("\u00ABextend\u00BB");
        extendRel.setToolTip(rs.getString("edge1.tooltip"));
        EDGE_PROTOTYPES.add(extendRel);

        UseCaseRelationshipEdge includeRel = new UseCaseRelationshipEdge();
//        includeRel.setBentStyle(BentStyleChoiceList.STRAIGHT);
//        includeRel.setLineStyle(LineStyleChoiceList.DOTTED);
//        includeRel.setEndArrowHead(ArrowheadChoiceList.V);
        includeRel.setCenterLabel("\u00ABinclude\u00BB");
        includeRel.setToolTip(rs.getString("edge2.tooltip"));
        EDGE_PROTOTYPES.add(includeRel);

        UseCaseRelationshipEdge generalization = new UseCaseRelationshipEdge();
//        generalization.setBentStyle(BentStyleChoiceList.STRAIGHT);
//        generalization.setLineStyle(LineStyleChoiceList.SOLID);
//        generalization.setEndArrowHead(ArrowheadChoiceList.TRIANGLE);
        generalization.setToolTip(rs.getString("edge3.tooltip"));
        EDGE_PROTOTYPES.add(generalization);

        NoteEdge noteEdge = new NoteEdge();
        noteEdge.setToolTip(rs.getString("edge4.tooltip"));
        EDGE_PROTOTYPES.add(noteEdge);
    }

}
