package 核心.标签;

import arc.util.serialization.Json;
import arc.util.serialization.JsonValue;

import java.util.Arrays;


public class 标签数值存储类 implements Json.JsonSerializable {
    private float[] arr = new float[核心标签.all.length];

    public void clear() {
        Arrays.fill(arr, 0);
    }

    public float get(核心标签 attr) {
        check();
        return arr[attr.id];
    }

    public void set(核心标签 attr, float value) {
        check();
        arr[attr.id] = value;
    }


    public void add(核心标签 attr, float value) {
        check();
        float a = get(attr);
        arr[attr.id] = a+value;
    }


    public void add(标签数值存储类 other) {

        check();
        other.check();
        for (int i = 0; i < arr.length; i++) {
            arr[i] += other.arr[i];
        }
    }

    public void add(标签数值存储类 other, float scl) {
        check();
        other.check();
        for (int i = 0; i < arr.length; i++) {
            arr[i] += other.arr[i] * scl;
        }
    }

    @Override
    public void write(Json json) {
        check();
        for (核心标签 at : 核心标签.all) {
            if (arr[at.id] != 0) {
                json.writeValue(at.name, arr[at.id]);
            }
        }
    }

    @Override
    public void read(Json json, JsonValue data) {
        check();
        for (核心标签 at : 核心标签.all) {
            arr[at.id] = data.getFloat(at.name, 0);
        }
    }

    private void check() {
        if (arr.length != 核心标签.all.length) {
            var last = arr;
            arr = new float[核心标签.all.length];
            System.arraycopy(last, 0, arr, 0, Math.min(last.length, arr.length));
        }
    }
}

