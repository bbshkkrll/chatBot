package com.sheets;


import java.io.IOException;
import java.security.GeneralSecurityException;

public class SheetServiceUpdater implements Runnable {

    private SheetsService sheetsService;

    public SheetServiceUpdater(SheetsService sheetsService) {
        this.sheetsService = sheetsService;
    }

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(3_000_000);
                sheetsService.updateOrCreatePostsList(sheetsService.getSheetsService());
                System.out.println("Обновил " + sheetsService.posts.size());
            } catch (IOException | GeneralSecurityException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
