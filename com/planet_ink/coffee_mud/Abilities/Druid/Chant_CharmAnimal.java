package com.planet_ink.coffee_mud.Abilities.Druid;

import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.common.*;
import com.planet_ink.coffee_mud.utils.*;
import java.util.*;

public class Chant_CharmAnimal extends Chant
{
	public String ID() { return "Chant_CharmAnimal"; }
	public String name(){ return "Charm Animal";}
	public String displayText(){return "(Charmed)";}
	public int quality(){return Ability.MALICIOUS;}
	protected int canAffectCode(){return 0;}
	protected int canTargetCode(){return CAN_MOBS;}
	public long flags(){return Ability.FLAG_CHARMING;}

	private MOB charmer=null;
	private MOB getCharmer()
	{
		if(charmer!=null) return charmer;
		if((invoker!=null)&&(invoker!=affected))
			charmer=invoker;
		else
		if((text().length()>0)&&(affected instanceof MOB))
		{
			Room R=((MOB)affected).location();
			if(R!=null)
				charmer=R.fetchInhabitant(text());
		}
		if(charmer==null) return invoker;
		return charmer;
	}
	
	public Environmental newInstance(){	return new Chant_CharmAnimal();}

	public boolean okAffect(Environmental myHost, Affect affect)
	{
		if((affected==null)||(!(affected instanceof MOB)))
			return true;

		MOB mob=(MOB)affected;

		// when this spell is on a MOBs Affected list,
		// it should consistantly prevent the mob
		// from trying to do ANYTHING except sleep
		if((affect.amITarget(mob))
		&&(Util.bset(affect.targetCode(),Affect.MASK_MALICIOUS))
		&&(affect.source()==mob.amFollowing()))
				unInvoke();
		if((affect.amISource(mob))
		&&(Util.bset(affect.targetCode(),Affect.MASK_MALICIOUS))
		&&(affect.target()==mob.amFollowing()))
		{
			mob.tell("You like "+mob.amFollowing().charStats().himher()+" too much.");
			return false;
		}
		else
		if((affect.amISource(mob))
		&&(!mob.isMonster())
		&&(affect.target() instanceof Room)
		&&(affect.targetMinor()==affect.TYP_LEAVE)
		&&(mob.amFollowing()!=null)
		&&(((Room)affect.target()).isInhabitant(mob.amFollowing())))
		{
			mob.tell("You don't want to leave your friend.");
			return false;
		}
		else
		if((affect.amISource(mob))
		&&(mob.amFollowing()!=null)
		&&(affect.sourceMinor()==Affect.TYP_NOFOLLOW))
		{
			mob.tell("You like "+mob.amFollowing().name()+" too much.");
			return false;
		}

		return super.okAffect(myHost,affect);
	}

	public boolean tick(Tickable ticking, int tickID)
	{
		if((affecting()==null)||(!(affecting() instanceof MOB)))
			return false;
		MOB mob=(MOB)affecting();
		if((affected==mob)&&((mob.amFollowing()==null)||(mob.amFollowing()!=getCharmer())))
			ExternalPlay.follow(mob,getCharmer(),true);
		return super.tick(ticking,tickID);
	}

	public void unInvoke()
	{
		// undo the affects of this spell
		if((affected==null)||(!(affected instanceof MOB)))
			return;
		MOB mob=(MOB)affected;

		super.unInvoke();

		if(canBeUninvoked())
		{
			mob.tell("Your free-will returns.");
			ExternalPlay.follow(mob,null,false);
			ExternalPlay.standIfNecessary(mob);
			if((mob.isMonster())&&(!Sense.isMobile(mob)))
				CoffeeUtensils.wanderAway(mob,true,true);
		}
	}



	public boolean invoke(MOB mob, Vector commands, Environmental givenTarget, boolean auto)
	{
		MOB target=this.getTarget(mob,commands,givenTarget);
		if(target==null) return false;

		if(!Sense.isAnimalIntelligence(target))
		{
			mob.tell(target.name()+" is not an animal!");
			return false;
		}

		// the invoke method for spells receives as
		// parameters the invoker, and the REMAINING
		// command line parameters, divided into words,
		// and added as String objects to a vector.
		if(!super.invoke(mob,commands,givenTarget,auto))
			return false;

		// now see if it worked
		boolean success=profficiencyCheck(0,auto);
		if(success)
		{
			// it worked, so build a copy of this ability,
			// and add it to the affects list of the
			// affected MOB.  Then tell everyone else
			// what happened.
			String str=auto?"":"^S<S-NAME> chant(s) at <T-NAMESELF>.^?";
			FullMsg msg=new FullMsg(mob,target,this,affectType(auto),str);
			if(mob.location().okAffect(mob,msg))
			{
				mob.location().send(mob,msg);
				if(!msg.wasModified())
				{
					success=maliciousAffect(mob,target,0,Affect.MSK_CAST_VERBAL|Affect.TYP_MIND|(auto?Affect.MASK_GENERAL:0));
					if(success)
					{
						if(target.isInCombat()) target.makePeace();
						ExternalPlay.follow(target,mob,false);
						ExternalPlay.makePeaceInGroup(mob);
						if(target.amFollowing()!=mob)
							mob.tell(target.name()+" seems unwilling to follow you.");
					}
				}
			}
		}
		if(!success)
			return maliciousFizzle(mob,target,"<S-NAME> chant(s) at <T-NAMESELF>, but nothing happens.");

		// return whether it worked
		return success;
	}
}
