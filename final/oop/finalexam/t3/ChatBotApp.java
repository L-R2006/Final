package oop.finalexam.t3;

import java.io.*;
import java.net.URI;
import java.net.http.*;
import java.util.*;

public class ChatBotApp {

    private static String baseUrl;
    private static String botName;

    public static void main(String[] args) {
        try {
            
            Map<String, String> config = readConfig("config.txt");
            
            baseUrl = config.getOrDefault("url", "max.ge/final/t3/38475629/index.php");
            botName = config.getOrDefault("name", "ChatBot");

            System.out.println("Welcome to " + botName + "!");

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("\n" + botName + " - Choose an action:");
                System.out.println("1. View all blog posts");
                System.out.println("2. Create new blog post");
                System.out.println("3. View site statistics");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1" -> viewAllBlogs();
                    case "2" -> createNewBlog(scanner);
                    case "3" -> viewStatistics();
                    case "4" -> {
                        System.out.println("Goodbye from " + botName + "!");
                        running = false;
                    }
                    default -> System.out.println("Invalid choice, please try again.");
                }
            }

            scanner.close();
        } catch (IOException e) {
            System.err.println("Error reading config.txt: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Request interrupted: " + e.getMessage());
        }
    }

    
    private static Map<String, String> readConfig(String filename) throws IOException {
        Map<String, String> config = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && line.contains("=")) {
                    String[] parts = line.split("=", 2);
                    config.put(parts[0].trim(), parts[1].trim());
                }
            }
        }
        return config;
    }

    
    private static void viewAllBlogs() throws IOException, InterruptedException {
        String url = "https://" + baseUrl + "?api=blogs";
        String response = sendGetRequest(url);

        if (response == null || response.isEmpty()) {
            System.out.println("No response from server.");
            return;
        }

        System.out.println("\n--- Blog Posts ---");

        int dataIndex = response.indexOf("\"data\":");
        if (dataIndex == -1) {
            System.out.println("No blogs found.");
            return;
        }
        int start = response.indexOf('[', dataIndex);
        int end = response.indexOf(']', start);
        if (start == -1 || end == -1) {
            System.out.println("Malformed response.");
            return;
        }
        String blogsArray = response.substring(start + 1, end);

        if (blogsArray.trim().isEmpty()) {
            System.out.println("No blogs to display.");
            return;
        }

        String[] blogs = blogsArray.split("\\},");
        int count = 1;
        for (String blog : blogs) {
            if (!blog.trim().endsWith("}")) blog += "}";
            String title = extractStringValue(blog, "title");
            String content = extractStringValue(blog, "content");
            String author = extractStringValue(blog, "author");
            String id = extractStringValue(blog, "id");

            System.out.println("Post #" + count++);
            System.out.println("ID: " + id);
            System.out.println("Title: " + title);
            System.out.println("Content: " + content);
            System.out.println("Author: " + author);
            System.out.println("---------------------");
        }
    }

    
    private static void createNewBlog(Scanner scanner) throws IOException, InterruptedException {
        System.out.println("\n--- Create New Blog Post ---");
        System.out.print("Enter blog title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter blog content: ");
        String content = scanner.nextLine().trim();
        System.out.print("Enter author name: ");
        String author = scanner.nextLine().trim();

        if (title.isEmpty() || content.isEmpty() || author.isEmpty()) {
            System.out.println("Title, content, and author cannot be empty.");
            return;
        }

        String url = "https://" + baseUrl + "?api=blogs";
        String jsonBody = "{\"title\": \"" + escapeJson(title) + "\", \"content\": \"" + escapeJson(content) + "\", \"author\": \"" + escapeJson(author) + "\"}";

        String response = sendPostRequest(url, jsonBody);

        if (response != null && response.contains("\"success\":true")) {
            System.out.println("Blog post created successfully.");
        } else if (response != null && response.contains("400")) {
            System.out.println("Failed to create blog: Validation error or limit reached.");
        } else {
            System.out.println("Failed to create blog post.");
        }
    }

    
    private static void viewStatistics() throws IOException, InterruptedException {
        String url = "https://" + baseUrl + "?api=stats";
        String response = sendGetRequest(url);

        if (response == null || response.isEmpty()) {
            System.out.println("No response from server.");
            return;
        }

        int totalPosts = extractIntValue(response, "\"total_posts\":");
        int maxPosts = extractIntValue(response, "\"max_posts\":");
        int remainingPosts = extractIntValue(response, "\"remaining_posts\":");
        int percentageUsed = extractIntValue(response, "\"percentage_used\":");
        boolean canAddMore = response.contains("\"can_add_more\":true");

        System.out.println("\n--- Site Statistics ---");
        System.out.println("Total Posts: " + totalPosts);
        System.out.println("Max Posts Allowed: " + maxPosts);
        System.out.println("Remaining Posts Allowed: " + remainingPosts);
        System.out.println("Percentage Used: " + percentageUsed + "%");
        System.out.println("Can Add More Posts: " + (canAddMore ? "Yes" : "No"));
    }

    
    private static String sendGetRequest(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200 || response.statusCode() == 201) {
            return response.body();
        } else {
            System.out.println("Request failed with status code: " + response.statusCode());
            return null;
        }
    }

    
    private static String sendPostRequest(String url, String jsonBody) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200 || response.statusCode() == 201) {
            return response.body();
        } else {
            System.out.println("Request failed with status code: " + response.statusCode());
            return response.body();
        }
    }

   
    private static String extractStringValue(String json, String key) {
        int keyIndex = json.indexOf(key);
        if (keyIndex == -1) return "";
        int colonIndex = json.indexOf(':', keyIndex);
        if (colonIndex == -1) return "";
        int startQuote = json.indexOf('"', colonIndex + 1);
        if (startQuote == -1) return "";
        int endQuote = json.indexOf('"', startQuote + 1);
        if (endQuote == -1) return "";
        return json.substring(startQuote + 1, endQuote);
    }

    
    private static int extractIntValue(String json, String key) {
        int keyIndex = json.indexOf(key);
        if (keyIndex == -1) return -1;
        int colonIndex = json.indexOf(':', keyIndex);
        if (colonIndex == -1) return -1;
        int start = colonIndex + 1;
        while (start < json.length() && (json.charAt(start) == ' ' || json.charAt(start) == '\n')) start++;
        int end = start;
        while (end < json.length() && Character.isDigit(json.charAt(end))) end++;
        try {
            return Integer.parseInt(json.substring(start, end));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    
    private static String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
