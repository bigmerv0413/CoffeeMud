package com.planet_ink.coffee_mud.Items.Weapons;
import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.common.*;

public class Broom extends Quarterstaff
{
	public Broom()
	{
		super();
		myID=this.getClass().getName().substring(this.getClass().getName().lastIndexOf('.')+1);
		name="a broom";
		displayText="a broom lies in the corner of the room.";
		description="It's long and wooden, with lots of bristles on one end.";
		material=EnvResource.RESOURCE_OAK;
	}

	public Environmental newInstance()
	{
		return new Broom();
	}
}
