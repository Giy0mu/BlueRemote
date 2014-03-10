package Request;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.CompoundControl;
import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;


public class VolumeRequest extends AbstractRequest{
		
	public VolumeRequest(){
		super();
		mID = 0x02;
	}
	
	@Override
	byte[] exec(byte[] param) {
		float value = (float) param[2] / 100;
		System.out.println("before set speaker volume");
		setSpeakerVolume(value);
		return answer;
	}
	
	public void setSpeakerVolume(float volume){
		Port speaker = null;
		try
		{
			speaker = (Port) AudioSystem.getLine(Port.Info.SPEAKER);
			speaker.open();
			for (Control control : speaker.getControls())
			{
				String name = control.getType().toString();
				if (name.equals("Volume"))
				{
					FloatControl controlVolume = (FloatControl) control;
					controlVolume.setValue(volume);
				}
				else if (name.equals("Balance"))
				{
					FloatControl balance = (FloatControl) control;
					balance.setValue(balance.getMinimum() + (balance.getMaximum() - balance.getMinimum()) / 2.0F);
				}
				else if (name.equals("Mute"))
				{
					BooleanControl mute = (BooleanControl) control;
					mute.setValue(false);
				}
				else if (control instanceof CompoundControl)
				{
					CompoundControl cc = (CompoundControl) control;
					for (Control control1 : cc.getMemberControls())
					{
						String name1 = control1.getType().toString();
						if (name.equals("Microphone"))
						{
							if (name1.equals("Microphone Boost"))
							{
								BooleanControl boost = (BooleanControl) control1;
								boost.setValue(false);
							}
							else if (name1.equals("Volume"))
							{
								FloatControl controlVolume = (FloatControl) control1;
								controlVolume.setValue(volume);
							}
							else if (name1.equals("Mute"))
							{
								BooleanControl mute = (BooleanControl) control1;
								mute.setValue(true);
							}
						}
						else if (name.equals("Wave"))
						{
							if (name1.equals("Volume"))
							{
								FloatControl controlVolume = (FloatControl) control1;
								controlVolume.setValue(volume);
							}
							else if (name1.equals("Balance"))
							{
								FloatControl balance = (FloatControl) control1;
								balance.setValue(balance.getMinimum() + (balance.getMaximum() - balance.getMinimum()) / 2.0F);
							}
							else if (name1.equals("Mute"))
							{
								BooleanControl mute = (BooleanControl) control1;
								mute.setValue(false);
							}
						}
					}

				}
			}
			
		}
		catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		finally
		{
			speaker.close();
		}
	}

}
