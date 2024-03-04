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
    fullName VARCHAR(50) NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

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

-- Inserting sample data into the note table
INSERT INTO note (ownerId, Subject, Body)
VALUES 
    (1, 'Meeting', 'Meeting scheduled for tomorrow at 10 AM.'),
    (2, 'Shopping list', 'Need to buy groceries and household items.'),
    (1, 'Project deadline', 'Reminder: Project deadline is next week.'),
    (3, 'Travel plans', 'Planning a trip to Europe next month.'),
    (2, 'Birthday party', 'Inviting friends over for a birthday party.'),
    (1, 'Study group', 'Group study session at the library on Saturday.');
