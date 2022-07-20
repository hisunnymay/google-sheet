import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.api.client.http.HttpRequestInitializer;

import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SheetsQuickstart {

   /**
     * Update
     * @param requestInitializer - A parameter needed in Sheets.Builder
     * @param spreadsheetId - Id of the spreadsheet.
     * @param range - Range of cells of the spreadsheet.
     * @param valueInputOption - Determines how input data should be interpreted.
     * @param values - Update values
     * @return spreadsheet with updated values
     * @throws IOException - if credentials file not found.
     */

    public static UpdateValuesResponse updateValues(HttpRequestInitializer requestInitializer,
                                                    String spreadsheetId,
                                                    String range,
                                                    List<List<Object>> values,
                                                    String valueInputOption) throws IOException {

        // Create the sheets API client
        Sheets service = new Sheets.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer)
                .setApplicationName("Sheets samples")
                .build();


        UpdateValuesResponse result =null;

        try {
            // Updates the values in the specified range.
            ValueRange body = new ValueRange()
                    .setValues(values);
            result = service.spreadsheets().values()
                    .update(spreadsheetId, range, body)
                    .setValueInputOption(valueInputOption)
                    .execute();

            System.out.printf("%d cells updated.", result.getUpdatedCells());

        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 404) {
                System.out.printf("Spreadsheet not found with id '%s'.\n",spreadsheetId);
            } else {
                throw e;
            }
        }
        return result;
    };

    /**
     * Append
     * @param requestInitializer - A parameter needed in Sheets.Builder
     * @param spreadsheetId - Id of the spreadsheet.
     * @param range - Range of cells of the spreadsheet.
     * @param values - Append values
     * @param valueInputOption - Determines how input data should be interpreted.
     */

    public static AppendValuesResponse appendValues(HttpRequestInitializer requestInitializer,
                                    String spreadsheetId,
                                    String range,
                                    List<List<Object>> values,
                                    String valueInputOption) throws IOException {

        // Create the sheets API client
        Sheets service = new Sheets.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer)
                .setApplicationName("Sheets samples")
                .build();

        AppendValuesResponse result = null;


        try {
            // Append the values
            ValueRange body2 = new ValueRange()
                    .setValues(values);
            result = service.spreadsheets().values()
                    .append(spreadsheetId, range, body2)
                    .setValueInputOption(valueInputOption)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
     * Sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/10nehErITZaCFmQPKJpGQRieAbDs_r3Oiz44NP24Zz2Q/edit#gid=1169207060
     */
    public static void main(String... args) throws IOException, GeneralSecurityException {

        // Load pre-authorized user credentials from the environment.
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault()
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
                credentials);


        final String spreadsheetId = "10nehErITZaCFmQPKJpGQRieAbDs_r3Oiz44NP24Zz2Q";
        final String valueInputOption = "RAW";

        // Update values
        Object[] array={"Test"};
        List<Object> list1=new ArrayList<Object>();
        list1.add(array[0]);
        List<List<Object>> update_values = new ArrayList<List<Object>>();
        update_values.add(list1);

        // Update sheet: replace B2 with "Test"
        UpdateValuesResponse res1 = updateValues(requestInitializer, spreadsheetId,"'Test2'!B2:C3", update_values, valueInputOption);
        System.out.printf("\n // getUpdatedCells: " + "%o\n", res1.getUpdatedCells());

        // Append values
        String a = "3";
        String b = "Sunny";
        String c = "/";
        Object[] array1={a};
        Object[] array2={b};
        Object[] array3={c};

        List<Object> list2=new ArrayList<Object>();
        list2.add(array1[0]);
        list2.add(array2[0]);
        list2.add(array3[0]);

        List<List<Object>> append_values = new ArrayList<List<Object>>();
        append_values.add(list2);

        // Update sheet: Append a row ["3", "Sunny", "/"]
        AppendValuesResponse res2 = appendValues(requestInitializer, spreadsheetId,"'Test2'!A:D", append_values, valueInputOption);
        System.out.printf("\n// getUpdates().getUpdatedCells(): " + "%o\n", res2.getUpdates().getUpdatedCells());
    }
}
