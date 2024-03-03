-- Create the g_note database
CREATE DATABASE IF NOT EXISTS g_note;
use g_note;
-- Create the user table
CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(25) NOT NULL,
    password VARCHAR(25) NOT NULL,
    isAdmin BOOLEAN DEFAULT FALSE,
    isSubscribed BOOLEAN DEFAULT FALSE,
    fullName VARCHAR(50) NOT NULL
);


-- Create the note table
CREATE TABLE IF NOT EXISTS note (
    idNote INT AUTO_INCREMENT PRIMARY KEY,
    ownerId INT,
    Subject VARCHAR(255),
    Body TEXT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (ownerId) REFERENCES user(id)
);

-- Inserting sample data into the user table
INSERT INTO user (email, password, isAdmin, isSubscribed)
VALUES 
    ('user1@example.com', 'password1', false, true),
    ('user2@example.com', 'password2', false, false),
    ('admin@example.com', 'adminpassword', true, true);
