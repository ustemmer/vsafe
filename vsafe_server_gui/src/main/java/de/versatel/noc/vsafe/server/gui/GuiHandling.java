package de.versatel.noc.vsafe.server.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import de.versatel.noc.vsafe.shared.core.util.IconLibrary;

import de.versatel.noc.vSafe_Server.bl.HelperMethods;
import de.versatel.noc.vSafe_Server.database.MySQL_ConnectionObject;

import de.versatel.noc.vSafe_Server.mvc.controllers.DebuggingController;
import de.versatel.noc.vSafe_Server.mvc.controllers.MySQLConnectionListController;
import de.versatel.noc.vSafe_Server.mvc.controllers.MySQLPoolController;
import de.versatel.noc.vSafe_Server.mvc.controllers.RMIServerController;
import de.versatel.noc.vSafe_Server.mvc.controllers.SystemPropertiesController;

import de.versatel.noc.vSafe_Server.mvc.views.DebuggingViewPanel;
import de.versatel.noc.vSafe_Server.mvc.views.MySQLPoolViewPanel;
import de.versatel.noc.vSafe_Server.mvc.views.MySQLPoolStateViewPanel;
import de.versatel.noc.vSafe_Server.mvc.views.RMIServerStateViewPanel;
import de.versatel.noc.vSafe_Server.mvc.views.SystemPropertiesViewPanel;
import de.versatel.noc.vSafe_Server.mvc.views.RMIServerViewPanel;

import de.versatel.noc.vSafe_Server.mvc.models.DebuggingModel;
import de.versatel.noc.vSafe_Server.mvc.models.MySQLConnectionObjectModel;
import de.versatel.noc.vSafe_Server.mvc.models.MySQLPoolModel;
import de.versatel.noc.vSafe_Server.mvc.models.RMIServerModel;
import de.versatel.noc.vSafe_Server.mvc.models.SystemPropertiesModel;
import de.versatel.noc.vSafe_Server.mvc.models.SystemSettingsModel;
import de.versatel.noc.vSafe_Server.mvc.models.UserGroupModel;
import de.versatel.noc.vSafe_Server.mvc.models.UserModel;
import de.versatel.noc.vSafe_Server.mvc.models.UserSessionModel;

import de.versatel.noc.vSafe_Server.system.SystemManager;
import de.versatel.noc.vsafe.vsafe.shared.core.util.IconLibrary;

/**
 *
 * @author ulrich.stemmer
 */
public class GuiHandling {

    private IconLibrary iconLibrary;
    private SystemManager systemManager;
    private ImageIcon applicationIcon;
    //Frames
    private FrameMain frameMain;
    private FrameStart frameStart;
    private JFrame currentFrame;
    // InternalFrames
    private VSafeJInternalFrame debuggingFrame;
    private VSafeJInternalFrame loggingFrame;
    private VSafeJInternalFrame initialFrame;
    private VSafeJInternalFrame propertiesFrame;
    private VSafeJInternalFrame poolframe;
    private VSafeJInternalFrame userControlFrame;
    private VSafeJInternalFrame sessionFrame;
    private VSafeJInternalFrame rMIFrame;
    // Panels (Views)
    private DebuggingViewPanel debuggingPanel;
    private MySQLPoolViewPanel poolPanel;
    private MySQLPoolStateViewPanel poolStatePanel;
    private RMIServerViewPanel rMIServerPanel;
    private RMIServerStateViewPanel rMIStatePanel;
    private SystemPropertiesViewPanel sysPropsPanel;
    //private UserControlPanel userControlPanel;
    // Controllers
    private DebuggingController debuggingController;
    private MySQLPoolController mySQLPoolController;
    private MySQLConnectionListController mySQLConnectionListController;
    private RMIServerController rMIController;
    private SystemPropertiesController systemPropertiesController;
    //private UserController userController;
    // Models
    private DebuggingModel debuggingModel;
    private MySQLPoolModel mySQLPoolModel;
    private RMIServerModel rMIServerModel;
    private SystemPropertiesModel systemPropertiesModel;
    private SystemSettingsModel SystemSettingsModel;
    private UserGroupModel userGroupModel;
    private UserModel userModel;
    private UserSessionModel userSessionModel;

    public GuiHandling(SystemManager systemManager) {
        iconLibrary = new IconLibrary();
        this.systemManager = systemManager;
        this.applicationIcon = systemManager.getSystemProperties().getApplicationIcon();
        frameStart = new FrameStart(this);
        frameStart.setVisible(true);
        frameMain = new FrameMain(this);
        frameMain.setVisible(false);
    }

    public void closeDebuggingFrame() {
        if (debuggingController != null) {
            debuggingController.removeView(debuggingPanel);
        }
        debuggingPanel = null;
        debuggingFrame = null;
    }

    public void closePoolFrame() {
        if (mySQLPoolController != null) {
            mySQLPoolController.removeView(poolPanel);
        }
        poolPanel = null;
        poolframe = null;
    }

    public void closeSettingFrame() {
        if (mySQLPoolController != null) {
            mySQLPoolController.removeView(sysPropsPanel);
        }

        if (rMIController != null) {
            rMIController.removeView(sysPropsPanel);
        }
        if (systemPropertiesController != null) {
            systemPropertiesController.removeView(sysPropsPanel);
        }
        sysPropsPanel = null;
        propertiesFrame = null;
    }

    public ImageIcon getApplicationIcon() {
        return applicationIcon;
    }

    public JFrame getCurrentFrame() {
        return currentFrame;
    }

    public VSafeJInternalFrame getDebugFrame() {
        return this.debuggingFrame;
    }

    public FrameMain getFrameMain() {
        return frameMain;
    }

    public FrameStart getFrameStart() {
        return frameStart;
    }

    public IconLibrary getIconLibrary() {
        return iconLibrary;
    }

    public VSafeJInternalFrame getInitialFrame() {
        return initialFrame;
    }

    public VSafeJInternalFrame getLoggingFrame() {
        return loggingFrame;
    }

    public MySQLPoolController getPoolController() {
        return mySQLPoolController;
    }

    public MySQLPoolModel getPoolModel() {
        return mySQLPoolModel;
    }

    public MySQLPoolViewPanel getPoolPanel() {
        return poolPanel;
    }

    public MySQLPoolViewPanel getPoolViewPanel() {
        return poolPanel;
    }

    public VSafeJInternalFrame getPoolFrame() {
        return poolframe;
    }

    public MySQLPoolStateViewPanel getPoolStatePanel() {
        return poolStatePanel;
    }

    public VSafeJInternalFrame getPropertiesFrame() {
        return propertiesFrame;
    }

    public RMIServerController getRMIController() {
        return rMIController;
    }

    public VSafeJInternalFrame getRMIFrame() {
        return rMIFrame;
    }

    public RMIServerModel getRMIServerModel() {
        return rMIServerModel;
    }

    public RMIServerViewPanel getRMIServerPanel() {
        return rMIServerPanel;
    }

    public RMIServerStateViewPanel getRMIStatePanel() {
        return rMIStatePanel;
    }

    public VSafeJInternalFrame getSessionFrame() {
        return sessionFrame;
    }

    public SystemManager getSystemManager() {
        return this.systemManager;
    }

    public SystemPropertiesController getSystemPropertiesController() {
        return systemPropertiesController;
    }

    public SystemPropertiesModel getSystemPropertiesModel() {
        return systemPropertiesModel;
    }

    public SystemPropertiesViewPanel getSystemPropertiesPanel() {
        return sysPropsPanel;
    }

    public SystemSettingsModel getSystemSettingsModel() {
        return SystemSettingsModel;
    }

    public VSafeJInternalFrame getUserControlFrame() {
        return userControlFrame;
    }

    public UserGroupModel getUserGroupModel() {
        return userGroupModel;
    }

    public VSafeJInternalFrame getUserManagementFrame() {
        return userControlFrame;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public UserSessionModel getUserSessionModel() {
        return userSessionModel;
    }

    public void openDebuggingFrame() {
        if (debuggingFrame == null) {
            if (debuggingController == null) {
                debuggingController = new DebuggingController();
            }

            if (debuggingModel == null) {
                debuggingModel = new DebuggingModel(systemManager.getSystemUncaughtExceptionHandler());
                debuggingModel.addPropertyChangeListener(debuggingController);
                systemManager.getSystemUncaughtExceptionHandler().setDebuggingModel(debuggingModel);
                debuggingController.addModel(debuggingModel);
            }

            // View öffnen
            debuggingFrame = new VSafeJInternalFrame(this);
            debuggingFrame.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {

                public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                }

                public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                }

                public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                    closeDebuggingFrame();
                }

                public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
                }

                public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
                }

                public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
                }

                public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                }
            });

            debuggingFrame.setFrameIcon(systemManager.getSystemProperties().getApplicationIcon());
            debuggingFrame.setTitle(frameMain.getTitle() + " : Fehler");
            debuggingFrame.setClosable(true);
            debuggingFrame.setIconifiable(true);
            debuggingFrame.setResizable(true);
            debuggingFrame.setSize(new Dimension(400, 300));
            debuggingPanel = new DebuggingViewPanel(debuggingFrame, debuggingController);
            debuggingController.addView(debuggingPanel);
            debuggingFrame.add(poolPanel);
            frameMain.jDesktopPane1.add(poolframe);
            debuggingFrame.setVisible(true);
            debuggingFrame.pack();
            debuggingFrame.setLocation(
                    (frameMain.jDesktopPane1.getSize().width - poolframe.getSize().width) / 2,
                    (frameMain.jDesktopPane1.getSize().height - poolframe.getSize().height) / 2);
            mySQLPoolModel.updateViews();

        } else if (debuggingFrame.isIcon()) {
            try {
                debuggingFrame.setIcon(false);
            } catch (Exception e) {
                HelperMethods.showErrorMessage((Throwable) e,
                        frameMain.getContentPane());
            }
        } else {
            debuggingFrame.setVisible(true);
        }



    }

    public void openPoolFrame() {
        if (poolframe == null) {

            // Controller/Model Pool setzen
            if (mySQLPoolController == null) {
                mySQLPoolController = new MySQLPoolController();
            }

            if (mySQLPoolModel == null) {
                mySQLPoolModel = new MySQLPoolModel(systemManager.getMySQL_PoolHandling(), mySQLPoolController);
                systemManager.getMySQL_PoolHandling().setMySQLPoolModel(mySQLPoolModel);
                mySQLPoolController.addModel(mySQLPoolModel);
            }

            // Controller/Model Pool - ConnectionList setzen
            if (mySQLConnectionListController == null) {
                mySQLConnectionListController = new MySQLConnectionListController();
                List<MySQL_ConnectionObject> mcos = systemManager.getMySQL_PoolHandling().getConnectionObjects();
                for (MySQL_ConnectionObject mco : mcos) {
                    if (mco.getMySQL_ConnectionObjectModel() == null) {
                        MySQLConnectionObjectModel mcom = new MySQLConnectionObjectModel(mco);
                        mco.setMySQLPoolObjectModel(mcom);
                        mySQLConnectionListController.addModel(mcom);
                    }
                }
            }
            if (mySQLPoolController.getMySQLConnectionListController() != mySQLConnectionListController) {
                mySQLPoolController.setMySQLConnectionListController(mySQLConnectionListController);
            }

            // View öffnen
            poolframe = new VSafeJInternalFrame(this);
            poolframe.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {

                public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                }

                public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                }

                public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                    closePoolFrame();
                }

                public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
                }

                public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
                }

                public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
                }

                public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                }
            });

            poolframe.setFrameIcon(systemManager.getSystemProperties().getApplicationIcon());
            poolframe.setTitle(frameMain.getTitle() + " : Datenbankverbindungen");
            poolframe.setClosable(true);
            poolframe.setIconifiable(true);
            poolframe.setResizable(true);
            poolframe.setSize(new Dimension(1100, 250));
            poolPanel = new MySQLPoolViewPanel(poolframe, mySQLPoolController, mySQLConnectionListController);
            mySQLPoolController.addView(poolPanel);
            poolframe.add(poolPanel);
            frameMain.jDesktopPane1.add(poolframe);
            poolframe.setVisible(true);
            poolframe.pack();
            poolframe.setLocation(
                    (frameMain.jDesktopPane1.getSize().width - poolframe.getSize().width) / 2,
                    (frameMain.jDesktopPane1.getSize().height - poolframe.getSize().height) / 2);
            mySQLPoolModel.updateViews();

        } else if (poolframe.isIcon()) {
            try {
                poolframe.setIcon(false);
            } catch (Exception e) {
                HelperMethods.showErrorMessage((Throwable) e,
                        frameMain.getContentPane());
            }
        } else {
            poolframe.setVisible(true);
        }
    }

    public void openSettingFrame() {
        if (propertiesFrame == null) {
            propertiesFrame = new VSafeJInternalFrame(this);
            propertiesFrame.setFrameIcon(systemManager.getSystemProperties().getApplicationIcon());
            propertiesFrame.setTitle(frameMain.getTitle() + " : Systemeinstellungen");
            propertiesFrame.setClosable(true);
            propertiesFrame.setIconifiable(true);
            propertiesFrame.setResizable(true);

            propertiesFrame.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {

                public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                }

                public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                }

                public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                    closeSettingFrame();
                }

                public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
                }

                public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
                }

                public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
                }

                public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                }
            });



            // Controller setzen
            if (systemPropertiesController == null) {
                systemPropertiesController = new SystemPropertiesController();
            }
            if (rMIController == null) {
                rMIController = new RMIServerController();
            }
            if (mySQLPoolController == null) {
                mySQLPoolController = new MySQLPoolController();
            }

            // Models
            if (mySQLPoolModel == null) {
                mySQLPoolModel = new MySQLPoolModel(systemManager.getMySQL_PoolHandling(), mySQLPoolController);
                systemManager.getMySQL_PoolHandling().setMySQLPoolModel(mySQLPoolModel);
                mySQLPoolController.addModel(mySQLPoolModel);
            }
            if (rMIServerModel == null) {
                rMIServerModel = new RMIServerModel(systemManager.getRMIServerHandling());
                //systemManager.getRMIServerHandling()
                rMIController.addModel(rMIServerModel);
            }
            if (systemPropertiesModel == null) {
                systemPropertiesModel = new SystemPropertiesModel(systemManager.getSystemProperties());
                systemPropertiesController.addModel(systemPropertiesModel);
            }

            // Panel öffnen
            if (sysPropsPanel == null) {
                sysPropsPanel = new SystemPropertiesViewPanel(
                        propertiesFrame,
                        frameMain.getContentPane(),
                        systemPropertiesController,
                        rMIController,
                        mySQLPoolController);
                mySQLPoolController.addView(sysPropsPanel);
                rMIController.addView(sysPropsPanel);
                systemPropertiesController.addView(sysPropsPanel);
                //propertiesFrame.setContentPane(frameMain.getContentPane());

                propertiesFrame.add(sysPropsPanel);
                frameMain.jDesktopPane1.add(propertiesFrame);
                propertiesFrame.setVisible(true);
                propertiesFrame.pack();
                propertiesFrame.setLocation(
                        (frameMain.jDesktopPane1.getSize().width - propertiesFrame.getSize().width) / 2,
                        (frameMain.jDesktopPane1.getSize().height - propertiesFrame.getSize().height) / 2);

                mySQLPoolModel.updateViews();
                rMIServerModel.updateViews();
                systemPropertiesModel.updateViews();

            }

        } else if (propertiesFrame.isIcon()) {
            try {
                propertiesFrame.setIcon(false);
            } catch (Exception e) {
                HelperMethods.showErrorMessage((Throwable) e,
                        frameMain.getContentPane());
            }
        } else {
            propertiesFrame.setVisible(true);
        }
    }

    public void openUserFrame() {
        if (userControlFrame == null) {
            userControlFrame = new VSafeJInternalFrame(this);
            userControlFrame.setTitle(frameMain.getTitle() + " : User Management");
            userControlFrame.setFrameIcon(systemManager.getSystemProperties().getApplicationIcon());

            frameMain.jDesktopPane1.add(userControlFrame);
            try {
                /*
                 * Fenster zentrieren
                 */
                userControlFrame.setLocation(
                        (frameMain.jDesktopPane1.getSize().width - userControlFrame.getSize().width) / 2,
                        (frameMain.jDesktopPane1.getSize().height - userControlFrame.getSize().height) / 2);
            } catch (Exception e) {
                HelperMethods.showErrorMessage((Throwable) e,
                        frameMain.getContentPane());
            }
            userControlFrame.setVisible(true);

        } else if (userControlFrame.isIcon()) {
            try {
                userControlFrame.setIcon(false);
            } catch (Exception e) {
                HelperMethods.showErrorMessage((Throwable) e,
                        frameMain.getContentPane());
            }
        }
    }

    public void setApplicationIcon(ImageIcon icon) {
        applicationIcon = icon;
    }

    public void setCurrentFrame(JFrame frame) {
        currentFrame = frame;
    }

    public void setFrameStart(FrameStart frameStart) {
        this.frameStart = frameStart;
    }

    public void setInitialFrame(VSafeJInternalFrame initialFrame) {
        this.initialFrame = initialFrame;
    }

    public void setLoggingFrame(VSafeJInternalFrame loggingFrame) {
        this.loggingFrame = loggingFrame;
    }

    public void setPoolController(MySQLPoolController poolController) {
        this.mySQLPoolController = poolController;
    }

    public void setPoolStatePanel(MySQLPoolViewPanel poolStatePanel) {
        this.poolPanel = poolStatePanel;
    }

    public void setPoolframe(VSafeJInternalFrame poolframe) {
        this.poolframe = poolframe;
    }

    public void setPropertiesFrame(VSafeJInternalFrame propertiesFrame) {
        this.propertiesFrame = propertiesFrame;
    }

    public void setRMIController(RMIServerController rMIController) {
        this.rMIController = rMIController;
    }

    public void setRMIFrame(VSafeJInternalFrame rMIFrame) {
        this.rMIFrame = rMIFrame;
    }

    public void setRMIStatePanel(RMIServerStateViewPanel rMIStatePanel) {
        this.rMIStatePanel = rMIStatePanel;
    }

    public void setSessionFrame(VSafeJInternalFrame sessionFrame) {
        this.sessionFrame = sessionFrame;
    }

    public void setStartMessage(String message) {
        if (frameStart != null) {
            frameStart.setStartMessage(message);
        }
    }

    public void setSystemPropertiesController(SystemPropertiesController systemPropertiesController) {
        this.systemPropertiesController = systemPropertiesController;
    }

    public void setUserManagementFrame(VSafeJInternalFrame userControlFrame) {
        this.userControlFrame = userControlFrame;
    }

    public void setFrameMain(FrameMain frameMain) {
        this.frameMain = frameMain;
    }

    public void setIconLibrary(IconLibrary iconLibrary) {
        this.iconLibrary = iconLibrary;
    }

    public void setUserControlFrame(VSafeJInternalFrame userControlFrame) {
        this.userControlFrame = userControlFrame;
    }

    public void setSystemSettingsModel(SystemSettingsModel SystemSettingsModel) {
        this.SystemSettingsModel = SystemSettingsModel;
    }

    /*public MySQLConnectionObjectModel getMySQLConnectionObjectModel() {
    return mySQLConnectionObjectModel;
    }*/

    /*public void setMySQLConnectionObjectModel(MySQLConnectionObjectModel mySQLConnectionObjectModel) {
    this.mySQLConnectionObjectModel = mySQLConnectionObjectModel;
    }*/
    public void setMySQLPoolModel(MySQLPoolModel mySQLPoolModel) {
        this.mySQLPoolModel = mySQLPoolModel;
    }

    public void setPoolPanel(MySQLPoolViewPanel poolPanel) {
        this.poolPanel = poolPanel;
    }

    public void setPoolStatePanel(MySQLPoolStateViewPanel poolStatePanel) {
        this.poolStatePanel = poolStatePanel;
    }

    public void setRMIServerModel(RMIServerModel rMIServerModel) {
        this.rMIServerModel = rMIServerModel;
    }

    public void setRMIServerPanel(RMIServerViewPanel rMIServerPanel) {
        this.rMIServerPanel = rMIServerPanel;
    }

    public void setSysPropsPanel(SystemPropertiesViewPanel sysPropsPanel) {
        this.sysPropsPanel = sysPropsPanel;
    }

    public void setSystemPropertiesModel(SystemPropertiesModel systemPropertiesModel) {
        this.systemPropertiesModel = systemPropertiesModel;
    }

    public void setUserGroupModel(UserGroupModel userGroupModel) {
        this.userGroupModel = userGroupModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public void setUserSessionModel(UserSessionModel userSessionModel) {
        this.userSessionModel = userSessionModel;
    }

    public void startingFinished() {
        frameStart.dispose();
        frameMain.setIconImage(frameStart.getIconImage());
        frameMain.setLocation(0, 0);
        frameMain.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frameMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frameMain.setTitle(frameStart.getTitle());
        frameMain.setVisible(true);
        frameMain.repaint();
        frameMain.setVisible(true);
        setCurrentFrame(frameMain);
    }
}
