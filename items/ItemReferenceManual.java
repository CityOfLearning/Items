package com.dyn.fixins.items;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import com.dyn.DYNServerMod;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemReferenceManual extends ItemEditableBook {

	public ItemReferenceManual() {
		super();
	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye
	 * returns 16 items)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		try {
			URL manualUrl = new URL("http://broad-participation.s3.amazonaws.com/manualList.json");

			JsonParser parser = new JsonParser();
			JsonObject manualList = parser.parse(new JsonReader(new InputStreamReader(manualUrl.openStream())))
					.getAsJsonObject();

			JsonArray manualUrls = manualList.get("manuals").getAsJsonArray();
			for (JsonElement mUrl : manualUrls) {
				try {

					URL url = new URL(mUrl.getAsString());

					JsonObject robotDocJson = parser.parse(new JsonReader(new InputStreamReader(url.openStream())))
							.getAsJsonObject();

					JsonArray funcArray = robotDocJson.get("functions").getAsJsonArray();

					NBTTagList pagesNbt = new NBTTagList();

					for (JsonElement func : funcArray) {
						JsonObject jsonObj = func.getAsJsonObject();
						String text = EnumChatFormatting.GOLD + jsonObj.get("package").getAsString() + "\n"
								+ EnumChatFormatting.DARK_GREEN + jsonObj.get("short").getAsString() + "\n"
								+ EnumChatFormatting.BLUE + jsonObj.get("argument_list").getAsString() + "\n\n"
								+ EnumChatFormatting.BLACK + jsonObj.get("description").getAsString();

						pagesNbt.appendTag(new NBTTagString(text));
					}

					NBTTagCompound tag = new NBTTagCompound();
					tag.setString("author", "DYN Minecraft Team");
					tag.setString("title", "Python API Manual");
					tag.setTag("pages", pagesNbt);

					ItemStack is = new ItemStack(Items.written_book);
					is.setTagCompound(tag);

					subItems.add(is);
				} catch (Exception e) {
					DYNServerMod.logger.error("Could not create Manual", e);
					NBTTagList pagesNbt = new NBTTagList();
					pagesNbt.appendTag(new NBTTagString(
							"Could not create manual, something went wrong :(\n" + e.getLocalizedMessage()));

					NBTTagCompound tag = new NBTTagCompound();
					tag.setString("author", "DYN Minecraft Team");
					tag.setString("title", "API Manual");
					tag.setTag("pages", pagesNbt);

					ItemStack is = new ItemStack(Items.written_book);
					is.setTagCompound(tag);

					subItems.add(is);
				}
			}
		} catch (Exception e) {
			DYNServerMod.logger.error("Could not get manual list", e);
			NBTTagList pagesNbt = new NBTTagList();
			pagesNbt.appendTag(new NBTTagString(
					"Could not create manuals, something went wrong :(\n\n" + e.getLocalizedMessage()));

			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("author", "DYN Minecraft Team");
			tag.setString("title", "API Manual");
			tag.setTag("pages", pagesNbt);

			ItemStack is = new ItemStack(Items.written_book);
			is.setTagCompound(tag);

			subItems.add(is);
		}
	}

}
