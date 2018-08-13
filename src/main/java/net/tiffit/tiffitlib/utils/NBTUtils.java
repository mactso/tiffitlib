package net.tiffit.tiffitlib.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class NBTUtils {

	public static <K, V> NBTTagList serializeMap(Map<K, V> map, INBTSerializer<K> keySerializer, INBTSerializer<V> valueSerializer){
		NBTTagList tag = new NBTTagList();
		for(Entry<K, V> entry : map.entrySet()){
			NBTTagCompound entryTag = new NBTTagCompound();
			keySerializer.serialize(entryTag, entry.getKey());
			valueSerializer.serialize(entryTag, entry.getValue());
			tag.appendTag(entryTag);
		}
		return tag;
	}
	
	public static <K, V> LinkedHashMap<K, V> deserializeMap(NBTTagList list, INBTDeserializer<K> keyDeserializer, INBTDeserializer<V> valueDeserializer){
		LinkedHashMap<K, V> map = new LinkedHashMap<K, V>();
		for(int i = 0; i < list.tagCount(); i++){
			NBTTagCompound tag = list.getCompoundTagAt(i);
			map.put(keyDeserializer.deserialize(tag), valueDeserializer.deserialize(tag));
		}
		return map;
	}
	
	public static interface INBTSerializer<V>{
		public void serialize(NBTTagCompound tag, V obj);
	}
	
	public static interface INBTDeserializer<V>{
		public V deserialize(NBTTagCompound tag);
	}
}
