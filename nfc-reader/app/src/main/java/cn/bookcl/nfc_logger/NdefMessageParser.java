/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package cn.bookcl.nfc_logger;

import java.util.ArrayList;
import java.util.List;

import cn.bookcl.nfc_logger.record.ParsedNdefRecord;
import cn.bookcl.nfc_logger.record.SmartPoster;
import cn.bookcl.nfc_logger.record.TextRecord;
import cn.bookcl.nfc_logger.record.UriRecord;
import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Utility class for creating {@link ParsedNdefMessage}s.
 */
public class NdefMessageParser {

    // Utility class
    private NdefMessageParser() {

    }

    /** Parse an NdefMessage */
    public static List<ParsedNdefRecord> parse(NdefMessage message) {
        Log.w("james_nfc_log", "NdefMessageParser parse 002 ---------");
        return getRecords(message.getRecords());
    }

    public static List<ParsedNdefRecord> getRecords(NdefRecord[] records) {
        List<ParsedNdefRecord> elements = new ArrayList<ParsedNdefRecord>();
        for (final NdefRecord record : records) {
            if (UriRecord.isUri(record)) {
                elements.add(UriRecord.parse(record));
            } else if (TextRecord.isText(record)) {
                elements.add(TextRecord.parse(record));
            } else if (SmartPoster.isPoster(record)) {
                elements.add(SmartPoster.parse(record));
            } else {
            	elements.add(new ParsedNdefRecord() {
					@Override
					public View getView(Activity activity, LayoutInflater inflater, ViewGroup parent, int offset) {
					    String record_payload;
				        TextView text = (TextView) inflater.inflate(R.layout.tag_text, parent, false);
				        record_payload=new String(record.getPayload());
//				        if ("Consys work time TAG --byJamesLee".equals(record_payload))
//				            text.setText("上班打卡时间: ");
//				        else
                            text.setText(record_payload);

                        Log.w("james_nfc_log", "text -->>>>" + record_payload  + "<<<--- org: " + record.toString());
				        return text;
					}
            		
            	});
            }
        }
        return elements;
    }
}
