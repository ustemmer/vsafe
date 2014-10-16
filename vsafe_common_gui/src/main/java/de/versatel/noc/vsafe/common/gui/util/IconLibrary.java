package de.versatel.noc.vsafe.common.gui.util;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author ulrich.stemmer
 */
public class IconLibrary {

    public static final String FILEBUTTONADD2_20X20_DISABLED = "fileButtonAdd2_20x20_Disabled";
    public static final String FILEBUTTONADD2_20X20_MOUSEENTERED = "fileButtonAdd2_20x20_MouseEntered";
    public static final String FILEBUTTONADD2_20X20_MOUSEEXITED = "fileButtonAdd2_20x20_MouseExited";
    public static final String FILEBUTTONADD2_20X20_MOUSEPRESSED = "fileButtonAdd2_20x20_MousePressed";
    public static final String FILEBUTTONCANCEL_20X20_DISABLED = "fileButtonCancel_20x20_Disabled";
    public static final String FILEBUTTONCANCEL_20X20_MOUSEENTERED = "fileButtonCancel_20x20_MouseEntered";
    public static final String FILEBUTTONCANCEL_20X20_MOUSEEXITED = "fileButtonCancel_20x20_MouseExited";
    public static final String FILEBUTTONCANCEL_20X20_MOUSEPRESSED = "fileButtonCancel_20x20_MousePressed";
    public static final String FILEBUTTONCANCEL2_20X20_DISABLED = "fileButtonCancel2_20x20_Disabled";
    public static final String FILEBUTTONCANCEL2_20X20_MOUSEENTERED = "fileButtonCancel2_20x20_MouseEntered";
    public static final String FILEBUTTONCANCEL2_20X20_MOUSEEXITED = "fileButtonCancel2_20x20_MouseExited";
    public static final String FILEBUTTONCANCEL2_20X20_MOUSEPRESSED = "fileButtonCancel2_20x20_MousePressed";
    public static final String FILEBUTTONEDIT2_20X20_DISABLED = "fileButtonEdit2_20x20_Disabled";
    public static final String FILEBUTTONEDIT2_20X20_MOUSEENTERED = "fileButtonEdit2_20x20_MouseEntered";
    public static final String FILEBUTTONEDIT2_20X20_MOUSEEXITED = "fileButtonEdit2_20x20_MouseExited";
    public static final String FILEBUTTONEDIT2_20X20_MOUSEPRESSED = "fileButtonEdit2_20x20_MousePressed";
    public static final String FILEBUTTONOK_20X20_DISABLED = "fileButtonOk_20x20_Disabled";
    public static final String FILEBUTTONOK_20X20_MOUSEENTERED = "fileButtonOk_20x20_MouseEntered";
    public static final String FILEBUTTONOK_20X20_MOUSEEXITED = "fileButtonOk_20x20_MouseExited";
    public static final String FILEBUTTONOK_20X20_MOUSEPRESSED = "fileButtonOk_20x20_MousePressed";
    public static final String FILEBUTTONOK2_20X20_DISABLED = "fileButtonOk2_20x20_Disabled";
    public static final String FILEBUTTONOK2_20X20_MOUSEENTERED = "fileButtonOk2_20x20_MouseEntered";
    public static final String FILEBUTTONOK2_20X20_MOUSEEXITED = "fileButtonOk2_20x20_MouseExited";
    public static final String FILEBUTTONOK2_20X20_MOUSEPRESSED = "fileButtonOk2_20x20_MousePressed";
    public static final String FILEBUTTONREMOVE2_20X20_DISABLED = "fileButtonRemove2_20x20_Disabled";
    public static final String FILEBUTTONREMOVE2_20X20_MOUSEENTERED = "fileButtonRemove2_20x20_MouseEntered";
    public static final String FILEBUTTONREMOVE2_20X20_MOUSEEXITED = "fileButtonRemove2_20x20_MouseExited";
    public static final String FILEBUTTONREMOVE2_20X20_MOUSEPRESSED = "fileButtonRemove2_20x20_MousePressed";
    public static final String FILEIBUTTON = "fileIButton";
    public static final String FILEIBUTTONOFF = "fileIButtonOff";
    public static final String FILEIBUTTONON = "fileIButtonOn";
    public static final String FILESOUTHAFRICA = "fileSouthAfrica";
    public static final String StateStoppedIcon = "";
    public static final String StateStartingBlinkingOnIcon = "";
    public static final String StateStartingBlinkingOffIcon = "";
    public static final String StateStartedIcon = "";
    public static final String StateStoppingBlinkingOnIcon = "";
    public static final String StateStoppingBlinkingOffIcon = "";
    private List<IconListObject> iconList;

    public IconLibrary() {
        iconList = new ArrayList<IconListObject>();
        iconList.add(new IconListObject(FILEBUTTONADD2_20X20_DISABLED, "pictures/gif/buttons/Button_Add_20x20_Disabled.gif"));
        iconList.add(new IconListObject(FILEBUTTONADD2_20X20_MOUSEENTERED, "pictures/gif/buttons/Button_Add_20x20_MouseEntered.gif"));
        iconList.add(new IconListObject(FILEBUTTONADD2_20X20_MOUSEEXITED, "pictures/gif/buttons/Button_Add_20x20_MouseExited.gif"));
        iconList.add(new IconListObject(FILEBUTTONADD2_20X20_MOUSEPRESSED, "pictures/gif/buttons/Button_Add_20x20_MousePressed.gif"));
        iconList.add(new IconListObject(FILEBUTTONCANCEL_20X20_DISABLED, "pictures/gif/buttons/Button-Circle-3D_Cancel_20x20_Disabled.gif"));
        iconList.add(new IconListObject(FILEBUTTONCANCEL_20X20_MOUSEENTERED, "pictures/gif/buttons/Button-Circle-3D_Cancel_20x20_MouseEntered.gif"));
        iconList.add(new IconListObject(FILEBUTTONCANCEL_20X20_MOUSEEXITED, "pictures/gif/buttons/Button-Circle-3D_Cancel_20x20_MouseExited.gif"));
        iconList.add(new IconListObject(FILEBUTTONCANCEL_20X20_MOUSEPRESSED, "pictures/gif/buttons/Button-Circle-3D_Cancel_20x20_MousePressed.gif"));
        iconList.add(new IconListObject(FILEBUTTONCANCEL2_20X20_DISABLED, "pictures/gif/buttons/Button_Cancel_20x20_Disabled.gif"));
        iconList.add(new IconListObject(FILEBUTTONCANCEL2_20X20_MOUSEENTERED, "pictures/gif/buttons/Button_Cancel_20x20_MouseEntered.gif"));
        iconList.add(new IconListObject(FILEBUTTONCANCEL2_20X20_MOUSEEXITED, "pictures/gif/buttons/Button_Cancel_20x20_MouseExited.gif"));
        iconList.add(new IconListObject(FILEBUTTONCANCEL2_20X20_MOUSEPRESSED, "pictures/gif/buttons/Button_Cancel_20x20_MousePressed.gif"));
        iconList.add(new IconListObject(FILEBUTTONEDIT2_20X20_DISABLED, "pictures/gif/buttons/Button_Edit_20x20_Disabled.gif"));
        iconList.add(new IconListObject(FILEBUTTONEDIT2_20X20_MOUSEENTERED, "pictures/gif/buttons/Button_Edit_20x20_MouseEntered.gif"));
        iconList.add(new IconListObject(FILEBUTTONEDIT2_20X20_MOUSEEXITED, "pictures/gif/buttons/Button_Edit_20x20_MouseExited.gif"));
        iconList.add(new IconListObject(FILEBUTTONEDIT2_20X20_MOUSEPRESSED, "pictures/gif/buttons/Button_Edit_20x20_MousePressed.gif"));
        iconList.add(new IconListObject(FILEBUTTONOK_20X20_DISABLED, "pictures/gif/buttons/Button-Circle-3D_Ok_20x20_Disabled.gif"));
        iconList.add(new IconListObject(FILEBUTTONOK_20X20_MOUSEENTERED, "pictures/gif/buttons/Button-Circle-3D_Ok_20x20_MouseEntered.gif"));
        iconList.add(new IconListObject(FILEBUTTONOK_20X20_MOUSEEXITED, "pictures/gif/buttons/Button-Circle-3D_Ok_20x20_MouseExited.gif"));
        iconList.add(new IconListObject(FILEBUTTONOK_20X20_MOUSEPRESSED, "pictures/gif/buttons/Button-Circle-3D_Ok_20x20_MousePressed.gif"));
        iconList.add(new IconListObject(FILEBUTTONOK2_20X20_DISABLED, "pictures/gif/buttons/Button_Ok_20x20_Disabled.gif"));
        iconList.add(new IconListObject(FILEBUTTONOK2_20X20_MOUSEENTERED, "pictures/gif/buttons/Button_Ok_20x20_MouseEntered.gif"));
        iconList.add(new IconListObject(FILEBUTTONOK2_20X20_MOUSEEXITED, "pictures/gif/buttons/Button_Ok_20x20_MouseExited.gif"));
        iconList.add(new IconListObject(FILEBUTTONOK2_20X20_MOUSEPRESSED, "pictures/gif/buttons/Button_Ok_20x20_MousePressed.gif"));
        iconList.add(new IconListObject(FILEBUTTONREMOVE2_20X20_DISABLED, "pictures/gif/buttons/Button_Remove_20x20_Disabled.gif"));
        iconList.add(new IconListObject(FILEBUTTONREMOVE2_20X20_MOUSEENTERED, "pictures/gif/buttons/Button_Remove_20x20_MouseEntered.gif"));
        iconList.add(new IconListObject(FILEBUTTONREMOVE2_20X20_MOUSEEXITED, "pictures/gif/buttons/Button_Remove_20x20_MouseExited.gif"));
        iconList.add(new IconListObject(FILEBUTTONREMOVE2_20X20_MOUSEPRESSED, "pictures/gif/buttons/Button_Remove_20x20_MousePressed.gif"));
        iconList.add(new IconListObject(FILEIBUTTON, "pictures/gif/buttons/i_20x20_green.gif"));
        iconList.add(new IconListObject(FILEIBUTTONOFF, "pictures/gif/buttons/20x20_i_circle_green_off_3D.gif"));
        iconList.add(new IconListObject(FILEIBUTTONON, "pictures/gif/buttons/20x20_i_circle_green_on_3D.gif"));
        iconList.add(new IconListObject(FILESOUTHAFRICA, "pictures/gif/ApplicationSymbols/flgrsa.gif"));

        iconList.add(new IconListObject(StateStoppedIcon, "pictures/gif/ApplicationSymbols/StateStoppedIcon.gif"));
        iconList.add(new IconListObject(StateStartingBlinkingOnIcon, "pictures/gif/ApplicationSymbols/StateStartingBlinkingOnIcon.gif"));
        iconList.add(new IconListObject(StateStartingBlinkingOffIcon, "pictures/gif/ApplicationSymbols/StateStartingBlinkingOffIcon.gif"));
        iconList.add(new IconListObject(StateStartedIcon, "pictures/gif/buttons/StateStartedIcon.gif"));
        iconList.add(new IconListObject(StateStoppingBlinkingOnIcon, "pictures/gif/buttons/StateStoppingBlinkingOnIcon.gif"));
        iconList.add(new IconListObject(StateStoppingBlinkingOffIcon, "pictures/gif/buttons/StateStoppingBlinkingOffIcon.gif"));
    }

    public ImageIcon getImageIcon(String iconName) {
        if (iconName != null && !iconName.isEmpty()) {
            for (IconListObject i : iconList) {
                if (i.getIconName().equals(iconName)) {
                    return new ImageIcon(i.getSourceFile());
                }
            }
        }
        return null;
    }

    private class IconListObject {

        private final String iconName;
        private String sourceFile;

        private IconListObject(String iconName, String sourceFile) {
            this.iconName = iconName;
            this.sourceFile = sourceFile;
        }

        public String getIconName() {
            return iconName;
        }

        public String getSourceFile() {
            return sourceFile;
        }

        public void setSourceFile(String sourceFile) {
            this.sourceFile = sourceFile;
        }
    }
}
