/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.example.com.dictionaryproviderexample;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.UserDictionary.Words;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * This is the central activity for the Provider Dictionary Example App. The purpose of this app is
 * to show an example of accessing the {@link Words} list via its' Content Provider.
 */
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the TextView which will be populated with the Dictionary ContentProvider data.
        TextView dictTextView = (TextView) findViewById(R.id.dictionary_text_view);

        // Get the ContentResolver which will send a message to the ContentProvider
        ContentResolver resolver = getContentResolver();

        // Get a Cursor containing all of the rows in the Words table

        // Surround the cursor in a try statement so that the finally block will eventually execute
        try (Cursor cursor = resolver.query(Words.CONTENT_URI, null, null, null, null)) {
            dictTextView.setText("The UserDictionary contains ");
            // -- YOUR CODE BELOW HERE -- //

            // Get the index of the column containing the actual words, using
            // UserDictionary.Words.WORD, which is the header of the word column.
            int idColumn = cursor.getColumnIndex(Words._ID);
            int freqColumn = cursor.getColumnIndex(Words.FREQUENCY);
            int wordColumn = cursor.getColumnIndex(Words.WORD);

            // Iterates through all returned rows in the cursor.
            while (cursor.moveToNext()) {
                // Use that index to extract the String value of the word
                // at the current row the cursor is on.
                int id = cursor.getInt(idColumn);
                String word = cursor.getString(wordColumn);
                int frequency = cursor.getInt(freqColumn);
                dictTextView.append(("\n" + id + " - " + frequency + " - " + word));
            }

            cursor.close();
        }
        // Always close your cursor to avoid memory leaks

    }
}
