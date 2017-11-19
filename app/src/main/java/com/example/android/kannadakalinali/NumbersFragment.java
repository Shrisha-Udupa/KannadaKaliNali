package com.example.android.kannadakalinali;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {

    public NumbersFragment() {
        // Required empty public constructor
    }


    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                        // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                        // our app is allowed to continue playing sound but at a lower volume. We'll treat
                        // both cases the same way because our app is playing short sound files.

                        // Pause playback and reset player to the start of the file. That way, we can
                        // play the word from the beginning when we resume playback.
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                        mMediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                        // Stop playback and clean up resources
                        releaseMediaPlayer();
                    }
                }
            };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.words_list, container, false);

        //Pasted from NumbersActivity's OnCreate() after the setContentView method call

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Words> wordList = new ArrayList<>();      //Creating a new ArrayList of Words
        //Making the wordList final so that it can be referenced inside the OnItemClickListener method
        wordList.add(new Words("One", "Ondhu", R.drawable.one, R.raw.ondhu));            //Adding Words to wordList inline
        wordList.add(new Words("Two", "Eradu", R.drawable.two, R.raw.eradu));
        wordList.add(new Words("Three", "Mooru", R.drawable.three, R.raw.mooru));
        wordList.add(new Words("Four", "Nalku", R.drawable.four, R.raw.nalku));
        wordList.add(new Words("Five", "Eidhu", R.drawable.five, R.raw.eidhu));
        wordList.add(new Words("Six", "Aaru", R.drawable.six, R.raw.aaru));
        wordList.add(new Words("Seven", "Yelu", R.drawable.seven, R.raw.yelu));
        wordList.add(new Words("Eight", "Yentu", R.drawable.eight, R.raw.yentu));
        wordList.add(new Words("Nine", "Ombatthu", R.drawable.nine, R.raw.ombatthu));
        wordList.add(new Words("Ten", "Hatthu", R.drawable.ten, R.raw.hatthu));

        WordsAdapter adapter = new WordsAdapter(getActivity(), wordList, R.color.category_numbers);   //Creating an adapter of type WordsAdapter whose data source is an ArayList called WordList for NumbersActivity
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //Since the OnItemClickListener is defined inline,here as an Anonymous class, its methods can only reference
            //local variables if they are declared final. Or it can reference global variables of the NUmbersActivity class
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words word = wordList.get(position);
                //Using log statements to print  the current state of an object to the logs
                Log.v("NumbersActivity", "Current word: " + word);


                //Release the media player if it currently exits without playing to completion
                // because we are about to play a different sound file
                releaseMediaPlayer();


                //Request Audio Focus
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //We have audio focus now


                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getmAudioResourceId());
                    mMediaPlayer.start();

                    //Set up a listener on the media player, so that we can stop and release the media player once the
                    //sounds has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                }
            }
        });


        return rootView;
    }




    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }


    }

    @Override
    public void onStop() {
        super.onStop();

        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }
}
