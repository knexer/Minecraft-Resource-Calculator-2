package untouchedwagons.minecraft.mcrc2.views;

import com.google.gson.JsonObject;

public interface IView<T> {
    public void process(T item);
    public JsonObject getJsonObject();
}
