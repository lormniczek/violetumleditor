package com.horstmann.violet.application.gui;

import com.horstmann.violet.framework.dialog.DialogFactory;
import com.horstmann.violet.framework.file.GraphFile;
import com.horstmann.violet.framework.file.IFile;
import com.horstmann.violet.framework.file.IGraphFile;
import com.horstmann.violet.framework.file.chooser.IFileChooserService;
import com.horstmann.violet.framework.file.naming.ExtensionFilter;
import com.horstmann.violet.framework.file.naming.FileNamingService;
import com.horstmann.violet.framework.file.persistence.IFileReader;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.BeanInjector;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.InjectedBean;
import com.horstmann.violet.framework.injection.resources.ResourceBundleInjector;
import com.horstmann.violet.framework.injection.resources.annotation.ResourceBundleBean;
import com.horstmann.violet.framework.plugin.IDiagramPlugin;
import com.horstmann.violet.framework.plugin.PluginRegistry;
import com.horstmann.violet.framework.userpreferences.UserPreferencesService;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.Workspace;
import com.thoughtworks.xstream.io.StreamException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

/**
 * Welcome window with "New project" and "Open" option
 * @Author Bartosz Hanc
 */
public class NewOpenFrame extends JFrame{

    /**
     * Constructor which create window and all buttons
     * @param mainFrame
     */
    public NewOpenFrame(final MainFrame mainFrame){
        super();
        this.mainFrame = mainFrame;
        initWindow();

        ResourceBundleInjector.getInjector().inject(this);
        BeanInjector.getInjector().inject(this);

        newButton = new JButton(newProject);
        newButton.setBounds(DEFAULT_X_BUTTONS, DEFAULT_Y_BUTTONS, WIDTH_BUTTONS, HEIGHT_BUTTONS);

        openButton = new JButton(openProject);
        openButton.setBounds(DEFAULT_X_BUTTONS, DEFAULT_Y_BUTTONS + 50, WIDTH_BUTTONS, HEIGHT_BUTTONS);

        add(newButton);
        add(openButton);

        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(newButton);
                remove(openButton);
                setSize(WIDTH_WINDOW, 390);
                generateDiagramsButtons();
                repaint();
            }
        });

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDir();
                dispose();
            }
        });

    }

    /**
     *  Sets window settings
     */
    private void initWindow(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        setLocationRelativeTo(null);
        setLayout(null);
        setAlwaysOnTop(true);
        setResizable(false);
    }

    /**
     * Methods which generates buttons, sets bounds, adds ActionListener to them and
     * adds them to NewOpenFrame
     */
    private void generateDiagramsButtons(){
        List<IDiagramPlugin> diagramPlugins = pluginRegistry.getDiagramPlugins();;
        JButton diagramButton;
        int yPositionIncrements = 0;
        for(final IDiagramPlugin iDiagramPlugin : diagramPlugins){
            diagramButton = new JButton(iDiagramPlugin.getName().replaceFirst("[0-9]*\\.", ""));
            diagramButton.setBounds(DEFAULT_X_BUTTONS, DEFAULT_Y_BUTTONS + yPositionIncrements, WIDTH_BUTTONS, HEIGHT_BUTTONS);
            diagramButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openDiagram(iDiagramPlugin);
                }
            });
            add(diagramButton);
            yPositionIncrements += 50;
        }
    }

    /**
     * Open windows Explorer where we can find our file
     */
    private void openDir(){
        IFile selectedFile = null;
        try
        {
            setVisible(false);
            ExtensionFilter[] filters = fileNamingService.getFileFilters();
            IFileReader fileOpener = fileChooserService.chooseAndGetFileReader(filters);
            if (fileOpener == null)
            {
                setVisible(true);
                return;
            }
            selectedFile = fileOpener.getFileDefinition();
            IGraphFile graphFile = new GraphFile(selectedFile);
            IWorkspace workspace = new Workspace(graphFile);
            mainFrame.addWorkspace(workspace);
        }
        catch (StreamException streamException)
        {
            dialogFactory.showErrorDialog(dialogOpenFileIncompatibilityMessage);
        }
        catch (IOException ioException)
        {
            dialogFactory.showErrorDialog(dialogOpenFileErrorMessage + " : " + ioException.getMessage());
        }
    }

    /**
     * Open new diagram on mainFrame
     * @param diagramPlugin
     */
    private void openDiagram(IDiagramPlugin diagramPlugin){
        Class<? extends IGraph> graphClass = diagramPlugin.getGraphClass();
        IGraphFile graphFile = new GraphFile(graphClass);
        IWorkspace diagramPanel = new Workspace(graphFile);
        String name = diagramPlugin.getName();
        name = name.replaceFirst("[0-9]*\\.", "");
        name = unsavedPrefix + " " + name.toLowerCase();
        diagramPanel.setTitle(name);
        mainFrame.addWorkspace(diagramPanel);
        dispose();
    }

    /**
     * Dimensions and default position of buttons and a NewOpenFrame
     */
    private final int HEIGHT_BUTTONS = 40;
    private final int WIDTH_BUTTONS = 250;
    private final int DEFAULT_X_BUTTONS = 10;
    private final int DEFAULT_Y_BUTTONS = 10;
    private final int HEIGHT_WINDOW = 150;
    private final int WIDTH_WINDOW = 285;

    /**
     * Reference to Main Frame
     */
    private MainFrame mainFrame;

    /**
     * Option buttons
     */
    private JButton newButton;
    private JButton openButton;

    /**
     * Access to user preferences
     */
    @InjectedBean
    private UserPreferencesService userPreferencesService;

    /**
     * DialogBox handler
     */
    @InjectedBean
    private DialogFactory dialogFactory;

    /**
     * File services
     */
    @InjectedBean
    private FileNamingService fileNamingService;

    /**
     * The file chooser to use open option
     */
    @InjectedBean
    private IFileChooserService fileChooserService;

    /**
     * Plugin registry
     */
    @InjectedBean
    private PluginRegistry pluginRegistry;

    @ResourceBundleBean(key = "workspace.unsaved_prefix")
    private String unsavedPrefix;

    @ResourceBundleBean(key = "dialog.open_file_content_incompatibility.text")
    private String dialogOpenFileIncompatibilityMessage;

    @ResourceBundleBean(key = "dialog.open_file_failed.text")
    private String dialogOpenFileErrorMessage;

    @ResourceBundleBean(key = "button.new")
    private String newProject;

    @ResourceBundleBean(key = "button.open")
    private String openProject;
}