package com.horstmann.violet.workspace.editorpart;

import com.horstmann.violet.framework.util.nodeusage.NodeUsage;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.node.INode;

import javax.swing.*;
import java.util.List;

/**
 * Defines the editor behaviour (an editor is something embedding an IGraph)
 *
 * @author Alexandre de Pellegrin
 */
public interface IEditorPart
{

    /**
     * Returns the graph handled by the editor
     */
    IGraph getGraph();

    /**
     * Removes the selected node or edges.
     */
    void removeSelected();

    /**
     * @return currently selected node
     */
    List<INode> getSelectedNodes();

    /**
     * Clears node and edges selection
     */
    void clearSelection();

    /**
     * Selects a node_old
     *
     * @param node
     */
    void selectElement(INode node);

    /**
     * Zooms in the editor about value specified in EditorProperties.properties.
     */
    void zoomIn();

    /**
     * Zooms out the editor about value specified in EditorProperties.properties.
     */
    void zoomOut();

    /**
     * @return current zoom factor
     */
    double getZoomFactor();

    /**
     * @return the grid used to keep elements aligned
     */
    IGrid getGrid();

    /**
     * Grows drawing area
     */
    void growDrawingArea();

    /**
     * Clips drawing area
     */
    void clipDrawingArea();

    /**
     * @return the awt object displaying this editor part
     */
    JComponent getSwingComponent();

    /**
     * @return object that manages node and edges selection
     */
    IEditorPartSelectionHandler getSelectionHandler();

    /**
     * @return manager used to declare new editor behaviors and how to send events between behaviors
     */
    IEditorPartBehaviorManager getBehaviorManager();

    /**
     * @return list of node names with it's usages
     */
    List<NodeUsage> getSelectedNodesUsages();
}