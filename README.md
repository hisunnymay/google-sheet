# google-sheet
Update Google Sheet

## Steps

1. In Intellj IDE, `./gradlew build` 
2. In Intellj IDE, `./gradlew run` 

## Table
[Sample Sheet](https://docs.google.com/spreadsheets/d/10nehErITZaCFmQPKJpGQRieAbDs_r3Oiz44NP24Zz2Q/edit#gid=1169207060)

![image](https://user-images.githubusercontent.com/95261974/178887810-d159d8ef-9185-4b13-9f49-9365b791f1a1.png)

## More
1. [Credential.json](/updateGoogleSheet/credentials.json) added in [gradlew](/updateGoogleSheet/gradlew)

2. Main: [SheetsQuickstart.java](updateGoogleSheet/src/main/java/SheetsQuickstart.java)

3. 调用 SDK 的代码
```
service.spreadsheets().values()
                    .update(spreadsheetId, range, body)
                    .setValueInputOption(valueInputOption)
                    .execute();
                    
service.spreadsheets().values()
                    .append(spreadsheetId, range, body2)
                    .setValueInputOption(valueInputOption)
                    .execute();
```
