package com.sheets;


import java.io.IOException;
import java.lang.reflect.Executable;
import java.security.GeneralSecurityException;

public class SheetServiceUpdater implements Runnable {

    private int lastPostsSize;

    private SheetsService sheetsService;

    public SheetServiceUpdater(SheetsService sheetsService) {
        this.sheetsService = sheetsService;
        this.lastPostsSize = sheetsService.posts.size();
    }

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(3_000);
                sheetsService.updateOrCreatePostsList(sheetsService.getSheetsService());
                if (sheetsService.posts.size() > lastPostsSize) {
                    System.out.printf("Добавлено %s постов", sheetsService.posts.size() - lastPostsSize);
                } else if (sheetsService.posts.size() < lastPostsSize) {
                    System.out.printf("Удалено %s постов", lastPostsSize - sheetsService.posts.size());
                } else {
                    System.out.println("Нет изменнений");
                }
                lastPostsSize = sheetsService.posts.size();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
