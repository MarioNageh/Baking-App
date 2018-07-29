package com.marionageh.bakingapp.Module;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Ingredients implements Parcelable {
    private float quantity;
    private String measure;
    private String ingredient;

    public Ingredients(float quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }


    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }


    public static final Creator<Ingredients> CREATOR = new Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel in) {
            return new Ingredients(in);
        }

        @Override
        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeFloat(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    public Ingredients(Parcel source) {
        // private float quantity;
        //    private String measure;
        //    private String ingredient;
        this.quantity = source.readFloat();
        this.measure = source.readString();
        ;
        this.ingredient = source.readString();
        ;

    }

}
