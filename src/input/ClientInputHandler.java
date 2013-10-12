package input;

import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import screens.MainScreen;
import tools.S;
import tools.T;

/**
 * ClientInputHandler - Handles all input from users and organizes them based on conditions.
 * @author SinisteRing
 */
public class ClientInputHandler{
    // Constant Variables:
    public static final float MOUSE_SENSITIVITY = 1;
    
    private static ActionListener mainScreenAction = new ActionListener(){
        public void onAction(String bind, boolean down, float tpf){
            if(!MainScreen.isActive()){
                return;
            }
            if(down){
                if(bind.equals("Click")){
                    MainScreen.handleClick();
                }
            }else{
                if(bind.equals("Click")){
                    MainScreen.handleUnclick();
                }
            }
        }
    };
    private static AnalogListener mainScreenAnalog = new AnalogListener(){
        public void onAnalog(String name, float value, float tpf){
            if(!MainScreen.isActive()){
                return;
            }
            MainScreen.update();
        }
    };
    /*
     * This will be utilized after the gameplay screen has been created and game actions are required.
    private static ActionListener gameAction = new ActionListener(){
        public void onAction(String bind, boolean down, float tpf){
            if(!inGameplay()){
                return;
            }
            // Movement:
            if(bind.equals("Move_Left")){
                MovementManager.setMove(MH.LEFT, down);
            }else if(bind.equals("Move_Right")){
                MovementManager.setMove(MH.RIGHT, down);
            }else if(bind.equals("Move_Forward")){
                MovementManager.setMove(MH.FORWARD, down);
            }else if(bind.equals("Move_Backward")){
                MovementManager.setMove(MH.BACKWARD, down);
            }else if(bind.equals("Move_Crouch")){
                MovementManager.setMove(MH.CROUCH, down);
            }else if(bind.equals("Move_Jump") && down){
                PlayerManager.getPlayer(ClientNetwork.getID()).getControl().jump();
            }
            // Actions:
            else if(bind.equals("Trigger_Right")){
                PlayerManager.getPlayer(ClientNetwork.getID()).setFiring(false, down);
            }else if(bind.equals("Trigger_Left")){
                PlayerManager.getPlayer(ClientNetwork.getID()).setFiring(true, down);
            }
            if(down){
                // Weapon Swapping:
                if(bind.equals("Swap")){
                    PlayerManager.getPlayer(ClientNetwork.getID()).swapGuns();
                }else if(bind.equals("Reload")){
                    PlayerManager.getPlayer(ClientNetwork.getID()).reload();
                }
                // Abilities:
                else if(bind.equals("Ability_1")){
                    ClientNetwork.send(new AbilityData(ClientNetwork.getID(), 0, new Ray(S.getCamera().getLocation(), S.getCamera().getDirection())));
                }else if(bind.equals("Ability_2")){
                    ClientNetwork.send(new AbilityData(ClientNetwork.getID(), 1, new Ray(S.getCamera().getLocation(), S.getCamera().getDirection())));
                }
                // Miscellaneous:
                else if(bind.equals("Misc_Key_1")){
                    TracerManager.toggle();
                }else if(bind.equals("Misc_Key_2")){
                    TracerManager.clear();
                    DecalManager.clear();
                }else if(bind.equals("Misc_Key_3")){
                    PlayerManager.getPlayer(ClientNetwork.getID()).getControl().setPhysicsLocation(new Vector3f(0, 110, 0));
                }else if(bind.equals("Misc_Key_4")){
                    World.toggleWireframe();
                }else if(bind.equals("Game_Menu")){
                    app.getMenuState().toggleGameMenu(true);
                }
            }
        }
    };*/
    /*
     * Not needed for current game, but could be useful in the future for mouse movement actions.
     * This could potentially be utilized to force the mouse cursor to the center, given the proper adjustments.
    private static AnalogListener gameAnalog = new AnalogListener(){
        public void onAnalog(String name, float value, float tpf) {
            if(!inGameplay()){
                return;
            }
            // Camera:
            if (name.equals("Mouse_Left")){
                RecoilManager.rotateCamera(value, MOUSE_SENSITIVITY, Vector3f.UNIT_Y);
            }else if (name.equals("Mouse_Right")){
                RecoilManager.rotateCamera(-value, MOUSE_SENSITIVITY, Vector3f.UNIT_Y);
            }else if (name.equals("Mouse_Up")){
                RecoilManager.rotateCamera(-value, MOUSE_SENSITIVITY, S.getCamera().getLeft());
            }else if (name.equals("Mouse_Down")){
                RecoilManager.rotateCamera(value, MOUSE_SENSITIVITY, S.getCamera().getLeft());
            }
        }
    };*/
    
    public static void initializeMenuInput(){
        // Mouse Movement:
        T.createMapping(S.getInputManager(), mainScreenAnalog, "Mouse_Left", new MouseAxisTrigger(MouseInput.AXIS_X, true));
        T.createMapping(S.getInputManager(), mainScreenAnalog, "Mouse_Right", new MouseAxisTrigger(MouseInput.AXIS_X, false));
        T.createMapping(S.getInputManager(), mainScreenAnalog, "Mouse_Up", new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        T.createMapping(S.getInputManager(), mainScreenAnalog, "Mouse_Down", new MouseAxisTrigger(MouseInput.AXIS_Y, true));
        
        // Actions:
        T.createMapping(S.getInputManager(), mainScreenAction, "Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        T.createMapping(S.getInputManager(), mainScreenAction, "RightClick", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        T.createMapping(S.getInputManager(), mainScreenAction, "Exit", new KeyTrigger(KeyInput.KEY_ESCAPE));
    }
    public static void initializeGame(){
        // Mouse Movement: (Not used currently)
        /*T.createMapping(inputManager, gameAnalog, "Mouse_Left", new MouseAxisTrigger(MouseInput.AXIS_X, true));
        T.createMapping(inputManager, gameAnalog, "Mouse_Right", new MouseAxisTrigger(MouseInput.AXIS_X, false));
        T.createMapping(inputManager, gameAnalog, "Mouse_Up", new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        T.createMapping(inputManager, gameAnalog, "Mouse_Down", new MouseAxisTrigger(MouseInput.AXIS_Y, true));*/
        // Movement:
        /*
         * These keys do not need to be mapped until the game screen is created.
        T.createMapping(inputManager, gameAction, "Move_Left", new KeyTrigger(KeyInput.KEY_A));
        T.createMapping(inputManager, gameAction, "Move_Right", new KeyTrigger(KeyInput.KEY_D));
        T.createMapping(inputManager, gameAction, "Move_Up", new KeyTrigger(KeyInput.KEY_W));
        T.createMapping(inputManager, gameAction, "Move_Down", new KeyTrigger(KeyInput.KEY_S));
        T.createMapping(inputManager, gameAction, "Move_Crouch", new KeyTrigger(KeyInput.KEY_LCONTROL));
        T.createMapping(inputManager, gameAction, "Move_Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        // Attacks:
        T.createMapping(inputManager, gameAction, "Trigger_Left", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        T.createMapping(inputManager, gameAction, "Trigger_Right", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        // Actions:
        T.createMapping(inputManager, gameAction, "Reload", new KeyTrigger(KeyInput.KEY_R));
        T.createMapping(inputManager, gameAction, "Swap", new KeyTrigger(KeyInput.KEY_Q));
        // Abilities:
        T.createMapping(inputManager, gameAction, "Ability_1", new KeyTrigger(KeyInput.KEY_1));
        T.createMapping(inputManager, gameAction, "Ability_2", new KeyTrigger(KeyInput.KEY_2));
        T.createMapping(inputManager, gameAction, "Ability_3", new KeyTrigger(KeyInput.KEY_3));
        T.createMapping(inputManager, gameAction, "Ability_4", new KeyTrigger(KeyInput.KEY_4));
        // Miscellaneous:
        T.createMapping(inputManager, gameAction, "Game_Menu", new KeyTrigger(KeyInput.KEY_ESCAPE));
        T.createMapping(inputManager, gameAction, "Misc_Key_1", new KeyTrigger(KeyInput.KEY_V));
        T.createMapping(inputManager, gameAction, "Misc_Key_2", new KeyTrigger(KeyInput.KEY_B));
        T.createMapping(inputManager, gameAction, "Misc_Key_3", new KeyTrigger(KeyInput.KEY_T));
        T.createMapping(inputManager, gameAction, "Misc_Key_4", new KeyTrigger(KeyInput.KEY_G));*/
    }
}
