package net.tiffit.tiffitlib.client;

import java.io.IOException;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.SoundEvents;

public class GuiChildScreen extends GuiScreen {

	protected GuiScreen parent;

	public GuiChildScreen(GuiScreen parent) {
		this.parent = parent;
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == 1) {
			this.mc.displayGuiScreen(parent);
			if (this.mc.currentScreen == null) {
				this.mc.setIngameFocus();
			}else{
				if(parent instanceof GuiChildScreen){
					((GuiChildScreen) parent).returnTo(this);
				}
	            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, .5F));
			}
		}
	}
	
	public void returnTo(GuiChildScreen child){
		
	}

}
