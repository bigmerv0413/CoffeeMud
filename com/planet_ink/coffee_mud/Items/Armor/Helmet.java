package com.planet_ink.coffee_mud.Items.Armor;
import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.common.*;
import com.planet_ink.coffee_mud.utils.*;

public class Helmet extends StdArmor
{
	public Helmet()
	{
		super();
		myID=this.getClass().getName().substring(this.getClass().getName().lastIndexOf('.')+1);
		name="a helmet";
		displayText="a helmet sits here.";
		description="This is fairly solid looking helmet.";
		properWornBitmap=Item.ON_HEAD;
		wornLogicalAnd=false;
		baseEnvStats().setArmor(10);
		baseEnvStats().setWeight(10);
		baseEnvStats().setAbility(0);
		baseGoldValue=16;
		material=EnvResource.RESOURCE_IRON;
		recoverEnvStats();
	}
	public Environmental newInstance()
	{
		return new Helmet();
	}
}
