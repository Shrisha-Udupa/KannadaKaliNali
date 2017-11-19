package com.example.android.kannadakalinali;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.android.kannadakalinali.R.color.category_colors;

/**
 * Created by Shrisha on 18-Mar-17.
 */

public class WordsAdapter extends ArrayAdapter<Words> {
    private int mColorResourceId;   //Resource ID for the background color for the list of words

    public WordsAdapter(Activity context, ArrayList<Words> wordList, int colorResourceId){
        super(context,0,wordList);
        mColorResourceId=colorResourceId;
        //0 is passed as the resource id since we are inflating the layout ourselves in the getView() method
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //getView method returns a list item view(which is one apecific view with data populated) to the parent ListView
        //position refers to the specific position Word object requested by the ListView
        //convertView refers to the view that is paassed to the adapter
        //parent refers to the ListView parent
        View listItemView=convertView;
        //Inflate the list item view with list_item.xml file  if it is empty
        if (listItemView==null)
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

        //Get the word object at the given position in the list
        Words currentWord=getItem(position);

        //Find the view pointed by the id by searching through the list item view layout and cast and save it in a textview
        //Get the kannada translation from the curentWord object and set this text on the kannada TextView
        TextView kannadaTextView=(TextView)listItemView.findViewById(R.id.kannada_textview);
        kannadaTextView.setText(currentWord.getmKannadaTranslation());

        TextView englishTextView=(TextView) listItemView.findViewById(R.id.english_textview);
        englishTextView.setText(currentWord.getmEnglishTranslation());


        ImageView imageView=(ImageView) listItemView.findViewById(R.id.image);
    //If the Words object currentWord has an image, use it and set visibility to VISIBLE
        if (currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getmImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        }
        else
            //If the Words object currentWord doesnt have an image, set visibility to GONE
            imageView.setVisibility(View.GONE);

        //Set the theme color for the list item
        View containerView=(View)listItemView.findViewById(R.id.container_view);
        //Find the color that the resource id maps to
        int color= ContextCompat.getColor(getContext(),mColorResourceId);
        //Set the background color of the container view
        containerView.setBackgroundColor(color);

        return listItemView;
    }
}
