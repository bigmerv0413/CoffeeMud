package com.planet_ink.coffee_mud.Libraries.interfaces;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.core.collections.*;
import com.planet_ink.coffee_mud.Abilities.interfaces.*;
import com.planet_ink.coffee_mud.Areas.interfaces.*;
import com.planet_ink.coffee_mud.Behaviors.interfaces.*;
import com.planet_ink.coffee_mud.CharClasses.interfaces.*;
import com.planet_ink.coffee_mud.Commands.interfaces.*;
import com.planet_ink.coffee_mud.Common.interfaces.*;
import com.planet_ink.coffee_mud.Exits.interfaces.*;
import com.planet_ink.coffee_mud.Items.interfaces.*;
import com.planet_ink.coffee_mud.Libraries.*;
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
import com.planet_ink.coffee_mud.Races.interfaces.*;

import java.io.IOException;
import java.util.*;
/*
   Copyright 2008-2015 Bo Zimmerman

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
/**
 * The Catalog Library maintains the prototypes of various Items and Mobs
 * who are keyed by their Name.  They can belong to categories to help
 * organize them, but that doesn't change the key requirements.  Individual
 * mobs and items are flagged as actually BEING an instance of a cataloged
 * prototype, and thus will change to reflect prototype changes when they
 * are able.  In addition to all this, certain statistics and information 
 * about catalog usage is maintained by the system.
 * @author Bo Zimmerman
 */
public interface CatalogLibrary extends CMLibrary
{
	/**
	 * Creates a list of all the item keys (names) of all the items
	 * in the catalog, regardless of catagory.
	 * @return a list of all the item keys
	 */
	public String[] getCatalogItemNames();

	/**
	 * Creates a list of all the item keys (names) of all the items
	 * in the catalog in the given catagory.  Send null to get all
	 * items regardless of catagory
	 * @param catagory the item catagory to filter by
	 * @return a list of all the item keys
	 */
	public String[] getCatalogItemNames(String catagory);

	/**
	 * Creates a list of all the mob keys (names) of all the mobs
	 * in the catalog, regardless of catagory.
	 * @return a list of all the mob keys
	 */
	public String[] getCatalogMobNames();

	/**
	 * Creates a list of all the mob keys (names) of all the mobs
	 * in the catalog in the given catagory.  Send null to get all
	 * mobs regardless of catagory
	 * @param catagory the mob catagory to filter by
	 * @return a list of all the mob keys
	 */
	public String[] getCatalogMobNames(String catagory);

	/**
	 * Creates a list of all the catagories that mobs have been
	 * placed in.
	 * @return a list of all the catagories
	 */
	public String[] getMobCatalogCatagories();

	/**
	 * Creates a list of all the catagories that items have been
	 * placed in.
	 * @return a list of all the catagories
	 */
	public String[] getItemCatalogCatagories();

	/**
	 * Changes the catagory of the catalog item with the given
	 * physical objects name to the given catagory.
	 * @param P the catalog item to get a key/name from
	 * @param catagory the new catagory, such as null
	 */
	public void setCatagory(Physical P, String catagory);

	/**
	 * Creates a list of all the prototype catalog items.
	 * @return a list of all the prototype catalog items.
	 */
	public Item[] getCatalogItems();

	/**
	 * Creates a list of all the prototype catalog mobs.
	 * @return a list of all the prototype catalog mobs.
	 */
	public MOB[] getCatalogMobs();

	/**
	 * Returns whether there exists an item/mob in the catalog
	 * of the same type and name/key as the given item or
	 * mob.
	 * @param E the item or mob
	 * @return true if it's in the catalog, false otherwise
	 */
	public boolean isCatalogObj(Environmental E);

	/**
	 * Returns whether there exists an item or mob in the catalog
	 * of the given name/key. Since the name could be item or mob,
	 * preference is given to mobs.
	 * @param name the item or mob name
	 * @return true if it's in the catalog, false otherwise
	 */
	public boolean isCatalogObj(String name);

	/**
	 * Returns the cataloged prototype item of the given name.
	 * @param name the name to look for.
	 * @return the cataloged prototype item
	 */
	public Item getCatalogItem(String name);

	/**
	 * Returns the cataloged prototype mob of the given name.
	 * @param name the name to look for.
	 * @return the cataloged prototype mob
	 */
	public MOB getCatalogMob(String name);

	/**
	 * Returns the cataloged prototype mob or item of the same type
	 * and with the same name as the given object.
	 * @param P the object type and name to look for
	 * @return the cataloged prototype mob or item
	 */
	public Physical getCatalogObj(Physical P);

	/**
	 * Returns the cataloged metadata for the item of the given name
	 * @see CataData
	 * @param name the name of the cataloged item.
	 * @return the cataloged metadata for the item
	 */
	public CataData getCatalogItemData(String name);

	/**
	 * Returns the cataloged metadata for the mob of the given name
	 * @see CataData
	 * @param name the name of the cataloged mob.
	 * @return the cataloged metadata for the mob
	 */
	public CataData getCatalogMobData(String name);

	/**
	 * Returns the cataloged metadata for the mob or item of the same type
	 * and with the same name as the given object.
	 * @see CataData
	 * @param P the object type and name to look for
	 * @return the cataloged metadata for the mob
	 */
	public CataData getCatalogData(Physical P);

	/**
	 * Creates a new catalog item or mob from the given item or mob
	 * in the given catagory. The given object is marked as cataloged
	 * and a copy if submitted to the database and memory.
	 * @param catagory the new catagory, such as null
	 * @param PA the item or mob to create in the catalog
	 */
	public void addCatalog(String catagory, Physical PA);

	/**
	 * Creates a new catalog item or mob from the given item or mob
	 * in no catagory. The given object is marked as cataloged
	 * and a copy if submitted to the database and memory.
	 * @param PA the item or mob to create in the catalog
	 */
	public void addCatalog(Physical PA);
	
	// doesn't database, or copy/mark the item
	public void submitToCatalog(Physical P);
	public void updateCatalogCatagory(Physical modelP, String newCat);
	public void delCatalog(Physical P);
	public void updateCatalog(Physical modelP);
	public StringBuffer checkCatalogIntegrity(Physical P);
	public void updateCatalogIntegrity(Physical P);
	public void changeCatalogUsage(Physical P, boolean add);
	public Item getDropItem(MOB M, boolean live);
	public CataData sampleCataData(String xml);
	public Vector<RoomContent> roomContent(Room R);
	public void updateRoomContent(String roomID, Vector<RoomContent> content);
	public void newInstance(Physical P);
	public void bumpDeathPickup(Physical P);
	public CMFile.CMVFSDir getCatalogRoot(CMFile.CMVFSDir resourcesRoot);

	public static interface RoomContent
	{
		public Physical P();
		public Environmental holder();
		public boolean isDirty();
		public void flagDirty();
		public boolean deleted();
	}

	public static interface CataData
	{
		public MaskingLibrary.CompiledZapperMask getMaskV();
		public String getMaskStr();
		public boolean getWhenLive();
		public double getRate();
		public void setMaskStr(String s);
		public void setWhenLive(boolean l);
		public void setRate(double r);
		public Enumeration<Physical> enumeration();
		public void addReference(Physical P);
		public boolean isReference(Physical P);
		public void delReference(Physical P);
		public int numReferences();
		public String mostPopularArea();
		public String randomRoom();
		public void cleanHouse();
		public Physical getLiveReference();
		public int getDeathsPicksups();
		public void bumpDeathPickup();
		public String category();
		public void setCatagory(String cat);

		public String data();

		public void build(String catadata);
	}
}
