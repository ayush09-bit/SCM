Smart Contact Manager (SCM)
Project Overview
Smart Contact Manager (SCM) is a feature-rich Spring Boot web application designed for managing personal and professional contacts with advanced features like secure authentication, contact search, image uploads, and role-based access control. Built with modern web technologies and best practices, SCM provides a clean, intuitive interface for users to organize and manage their contact information efficiently.

Version: 1.0.0
Framework: Spring Boot (Java)
Frontend: Thymeleaf + Tailwind CSS
Database: MySQL / PostgreSQL
Build Tool: Maven

Table of Contents
Features

Technology Stack

Project Architecture

Database Schema

API Endpoints

Controllers & Request Mappings

Data Models & Entities

Installation & Setup

Running the Application

Project Flow & User Journey

Development Best Practices

Contributing

License

Features
Authentication & Authorization
User Registration with email verification via token-based authentication

Secure Login with Spring Security configuration

Role-Based Access Control (RBAC) for users and admins

Email Token Verification for account activation

OAuth Support (ready for Google/GitHub social login integration)

Contact Management
Add Contacts with comprehensive details (name, email, phone, address, description)

View Contacts with pagination, sorting, and filtering

Update Contacts with real-time changes

Delete Contacts with cascading deletions

Mark Favorites for priority contacts

Add Social Links to contacts (LinkedIn, websites, custom links)

Upload Contact Pictures with Cloudinary integration

Search Functionality (search by name, email, phone number)

User Profile Management
Profile Management with editable user information

Profile Picture Upload

User Settings and preferences

Email Verification Status tracking

Phone Verification status tracking

Advanced Features
Pagination with customizable page size (default 10 items/page)

Sorting by multiple fields (name, email, creation date)

Search Filters for quick contact discovery

Image Service integration with Cloudinary for secure cloud storage

Email Service hooks for notification capabilities

Session Management with flash messages for user feedback

Technology Stack
Backend
Component	Technology
Language	Java 17+
Framework	Spring Boot 3.x
Web	Spring MVC
Security	Spring Security 6.x
Data Access	Spring Data JPA
Validation	Jakarta Validation (formerly javax.validation)
ORM	Hibernate
Build Tool	Maven
Logger	SLF4J with Logback
Frontend
Component	Technology
Template Engine	Thymeleaf
CSS Framework	Tailwind CSS
Styling	Custom CSS (input.css, output.css)
JavaScript	Vanilla JS + ES6
HTML	HTML5 Semantic Markup
Database
Component	Technology
Primary DB	MySQL 8.0+ / PostgreSQL 12+
Connection Pooling	HikariCP (default in Spring Boot)
Cloud & Storage
Component	Technology
Image Storage	Cloudinary API
Email Service	SMTP (configurable)
Deployment
Component	Technology
Containerization	Docker
Orchestration	Docker Compose
Project Architecture
Layered Architecture Pattern
text
┌─────────────────────────────────────────┐
│         Presentation Layer              │
│  (Thymeleaf Views + HTML/CSS/JS)       │
└─────────────────────────────────────────┘
                    ▲
                    │
┌─────────────────────────────────────────┐
│      Controller Layer                   │
│  (Request Handlers & Routing)           │
│  - PageController                       │
│  - AuthController                       │
│  - UserController                       │
│  - ContactController                    │
│  - ApiController                        │
│  - RootController (@ControllerAdvice)   │
└─────────────────────────────────────────┘
                    ▲
                    │
┌─────────────────────────────────────────┐
│       Service Layer                     │
│  (Business Logic)                       │
│  - UserServices                         │
│  - ContactService                       │
│  - imageService                         │
│  - EmailService                         │
└─────────────────────────────────────────┘
                    ▲
                    │
┌─────────────────────────────────────────┐
│     Repository Layer                    │
│  (Database Access via Spring Data JPA)  │
│  - UserRepository                       │
│  - ContactRepository                    │
└─────────────────────────────────────────┘
                    ▲
                    │
┌─────────────────────────────────────────┐
│      Entity / Domain Model              │
│  - User                                 │
│  - Contact                              │
│  - Social_links                         │
│  - Providers                            │
└─────────────────────────────────────────┘
                    ▲
                    │
┌─────────────────────────────────────────┐
│    Database (MySQL/PostgreSQL)          │
└─────────────────────────────────────────┘
Package Structure
text
com.Smart_Contact_Manager.demo
├── DemoApplication.java                  (Main Entry Point)
├── config/                               (Configuration Beans)
│   ├── SecurityConfig.java
│   ├── AuthFailureHandler.java
│   ├── OAuthAuthenticationSuccessHandler.java
│   └── SecurityCustomerUserDetailService.java
├── controllers/                          (MVC Controllers)
│   ├── PageController.java
│   ├── AuthController.java
│   ├── UserController.java
│   ├── ContactController.java
│   ├── ApiController.java
│   └── RootController.java (@ControllerAdvice)
├── services/                             (Business Logic)
│   ├── UserServices.java (interface)
│   ├── ContactService.java (interface)
│   ├── imageService.java (interface)
│   ├── EmailService.java (interface)
│   └── Impl/
│       ├── UserServicesImpl.java
│       ├── ContactServiceImpl.java
│       ├── imageServiceImpl.java
│       └── EmailServiceImpl.java
├── repositories/                         (Data Access)
│   ├── UserRepository.java
│   └── ContactRepository.java
├── users/                                (Entity Classes)
│   ├── User.java
│   ├── Contact.java
│   ├── Social_links.java
│   └── Providers.java (Enum)
├── form/                                 (DTO & Form Classes)
│   ├── UserForm.java
│   ├── ContactForm.java
│   └── ContactSearchForm.java
├── helper/                               (Utility & Helper Classes)
│   ├── Message.java
│   ├── MessageType.java
│   ├── Helper.java
│   ├── AppConstants.java
│   ├── SessionHelper.java
│   └── ResourceNotFoundException.java
├── validators/                           (Custom Validators)
│   ├── FileValidator.java
│   └── ValidFile.java (Custom Annotation)
└── resources/
    ├── application.properties
    ├── static/
    │   ├── css/
    │   │   ├── input.css
    │   │   ├── output.css
    │   │   └── style.css
    │   ├── js/
    │   │   ├── script.js
    │   │   ├── admin.js
    │   │   └── contacts.js
    │   └── images/
    └── templates/
        ├── base.html
        ├── navbar.html
        ├── user_navbar.html
        ├── home.html
        ├── about.html
        ├── services.html
        ├── contact.html
        ├── login.html
        ├── register.html
        └── user/
            ├── dashboard.html
            ├── profile.html
            ├── add_contact.html
            ├── contacts.html
            ├── search.html
            ├── update_contact_view.html
            └── contact_modals.html
Database Schema
Entity Relationship Diagram (ERD)
text
┌──────────────────────────────────────────────────┐
│                   USERS                          │
├──────────────────────────────────────────────────┤
│ PK │ userId (String, UUID)                       │
│    │ name (VARCHAR 255) NOT NULL                 │
│    │ email (VARCHAR 255) UNIQUE NOT NULL         │
│    │ password (VARCHAR 255)                      │
│    │ about (TEXT)                                │
│    │ profilepic (TEXT)                           │
│    │ phoneNumber (VARCHAR 20)                    │
│    │ enabled (BOOLEAN, default: false)           │
│    │ emailverified (BOOLEAN, default: false)     │
│    │ phoneverified (BOOLEAN, default: false)     │
│    │ provider (ENUM: SELF/GOOGLE/GITHUB)         │
│    │ provideruserId (VARCHAR 255)                │
│    │ emailToken (VARCHAR 500)                    │
│    │ roleList (Collection of Roles)              │
│    │ FK │ contact (List<Contact>)                │
└──────────────────────────────────────────────────┘
            │
            │ 1:N Relationship
            │
            ▼
┌──────────────────────────────────────────────────┐
│                   CONTACTS                       │
├──────────────────────────────────────────────────┤
│ PK │ contactId (String, UUID)                    │
│    │ name (VARCHAR 255)                          │
│    │ email (VARCHAR 255)                         │
│    │ phoneNumber (VARCHAR 20)                    │
│    │ address (VARCHAR 255)                       │
│    │ picture (TEXT/URL)                          │
│    │ description (TEXT, max 10000 chars)         │
│    │ favourite (BOOLEAN, default: false)         │
│    │ linkedInLink (VARCHAR 500)                  │
│    │ website (VARCHAR 500)                       │
│    │ cloudinaryPicPublicId (VARCHAR 255)         │
│    │ FK │ userId (FK to Users)                   │
│    │ FK │ links (List<Social_links>)             │
└──────────────────────────────────────────────────┘
            │
            │ 1:N Relationship
            │
            ▼
┌──────────────────────────────────────────────────┐
│              SOCIAL_LINKS                        │
├──────────────────────────────────────────────────┤
│ PK │ id (Long, Auto-generated)                   │
│    │ link (VARCHAR 500)                          │
│    │ title (VARCHAR 100)                         │
│    │ FK │ contactId (FK to Contacts)             │
└──────────────────────────────────────────────────┘
MySQL DDL (Create Table Statements)
sql
-- USERS TABLE
CREATE TABLE users (
    user_id VARCHAR(36) PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255),
    about TEXT,
    profilepic LONGTEXT,
    phone_number VARCHAR(20),
    enabled BOOLEAN DEFAULT FALSE,
    emailverified BOOLEAN DEFAULT FALSE,
    phoneverified BOOLEAN DEFAULT FALSE,
    provider ENUM('SELF', 'GOOGLE', 'GITHUB') NOT NULL DEFAULT 'SELF',
    provider_user_id VARCHAR(255),
    email_token VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_provider (provider)
);

-- ROLES TABLE (for role management - optional expansion)
CREATE TABLE user_roles (
    user_id VARCHAR(36),
    role VARCHAR(100),
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- CONTACTS TABLE
CREATE TABLE contacts (
    contact_id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    phone_number VARCHAR(20),
    address VARCHAR(255),
    picture LONGTEXT,
    description LONGTEXT,
    favourite BOOLEAN DEFAULT FALSE,
    linked_in_link VARCHAR(500),
    website VARCHAR(500),
    cloudinary_pic_public_id VARCHAR(255),
    user_id VARCHAR(36) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_name (name),
    INDEX idx_email (email),
    FULLTEXT INDEX ft_search (name, email, phone_number)
);

-- SOCIAL_LINKS TABLE
CREATE TABLE social_links (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    link VARCHAR(500),
    title VARCHAR(100),
    contact_id VARCHAR(36) NOT NULL,
    FOREIGN KEY (contact_id) REFERENCES contacts(contact_id) ON DELETE CASCADE,
    INDEX idx_contact_id (contact_id)
);
Table Descriptions
USERS Table
Column Name	Data Type	Constraints	Purpose
user_id	VARCHAR(36)	PRIMARY KEY	Unique identifier (UUID)
user_name	VARCHAR(255)	NOT NULL	User's full name
email	VARCHAR(255)	UNIQUE, NOT NULL	Email address (login credential)
password	VARCHAR(255)		Encrypted password
about	TEXT		User biography/description
profilepic	LONGTEXT		User's profile picture URL
phone_number	VARCHAR(20)		Phone number for contact
enabled	BOOLEAN	DEFAULT FALSE	Account activation status
emailverified	BOOLEAN	DEFAULT FALSE	Email verification flag
phoneverified	BOOLEAN	DEFAULT FALSE	Phone verification flag
provider	ENUM	DEFAULT 'SELF'	Auth provider (SELF, GOOGLE, GITHUB)
provider_user_id	VARCHAR(255)		External provider's user ID
email_token	VARCHAR(500)		Token for email verification
roleList	Collection		User roles (ROLE_USER, ROLE_ADMIN)
CONTACTS Table
Column Name	Data Type	Constraints	Purpose
contact_id	VARCHAR(36)	PRIMARY KEY	Unique identifier (UUID)
name	VARCHAR(255)		Contact's name
email	VARCHAR(255)		Contact's email
phone_number	VARCHAR(20)		Contact's phone
address	VARCHAR(255)		Contact's address
picture	LONGTEXT		Contact's picture URL (Cloudinary)
description	LONGTEXT		Detailed description (max 10000 chars)
favourite	BOOLEAN	DEFAULT FALSE	Mark as favorite
linked_in_link	VARCHAR(500)		LinkedIn profile URL
website	VARCHAR(500)		Website URL
cloudinary_pic_public_id	VARCHAR(255)		Cloudinary public ID for deletion
user_id	VARCHAR(36)	FOREIGN KEY	Reference to Users table
SOCIAL_LINKS Table
Column Name	Data Type	Constraints	Purpose
id	BIGINT	PRIMARY KEY, AUTO INCREMENT	Unique identifier
link	VARCHAR(500)		Social media/web link URL
title	VARCHAR(100)		Link title/platform name
contact_id	VARCHAR(36)	FOREIGN KEY	Reference to Contacts table
API Endpoints
Public Endpoints (No Authentication Required)
Home & Information Pages
HTTP Method	Endpoint	Description	Response
GET	/	Home page	home.html view
GET	/home	Home page (alternate)	home.html view
GET	/about	About page	about.html view
GET	/services	Services page	services.html view
GET	/contact	Contact page	contact.html view
Authentication Endpoints
HTTP Method	Endpoint	Description	Parameters	Response
GET	/login	Login page form		login.html view
GET	/register	Registration page		register.html view
POST	/do-register	Process user registration	UserForm (name, email, phone, password, about)	Redirect to /register with success message
GET	/auth/verify-email	Email verification link	token (query param)	Redirect to /login with verification status
Protected Endpoints (Authentication Required)
User Dashboard & Management
HTTP Method	Endpoint	Description	Parameters	Response
GET	/user/dashboard	User dashboard with contacts list	page, size, sortBy, direction	user/dashboard.html
GET	/user/profile	User profile page		user/profile.html
POST	/user/profile	Update user profile	User profile data	Redirect with success message
Contact Management Endpoints
HTTP Method	Endpoint	Description	Parameters	Response
GET	/user/contacts/add	Add contact form page		user/add_contact.html
POST	/user/contacts/add	Save new contact	ContactForm (name, email, phone, address, description, linkedInLink, website, contactImage, favourite)	Redirect to /user/contacts/add with success message
GET	/user/contacts	View all user's contacts with pagination	page, size, sortBy, direction	user/contacts.html
GET	/user/contacts/search	Search contacts	field (name/email/phone), value, page, size, sortBy, direction	user/search.html
GET	/user/contacts/view/{contactId}	View/Edit contact form	contactId (path variable)	user/update_contact_view.html
POST	/user/contacts/update/{contactId}	Update existing contact	contactId, ContactForm data	Redirect to /user/contacts/view/{contactId}
GET	/user/contacts/delete/{contactId}	Delete contact	contactId (path variable)	Redirect to /user/contacts with success message
REST API Endpoints
HTTP Method	Endpoint	Description	Parameters	Response
GET	/api/contacts/{contactId}	Get contact by ID (JSON)	contactId (path variable)	Contact object (JSON)
Controllers & Request Mappings
1. PageController
Base Path: None (Root paths)

Purpose: Handles public-facing pages and user registration

Endpoints:

text
GET  /              → home.html
GET  /home          → home.html
GET  /about         → about.html
GET  /services      → services.html
GET  /contact       → contact.html
GET  /login         → login.html
GET  /register      → register.html + UserForm model
POST /do-register   → Process registration + email verification
Key Methods:

index() - Home page

signuppage() - Registration form with UserForm model

processRegister() - Validates and saves user, sends verification email

2. AuthController
Base Path: /auth

Purpose: Handles authentication-related operations

Endpoints:

text
GET /auth/verify-email?token={token}  → Verify email and enable account
Key Methods:

verifyEmail() - Email token verification, enables user account

Features:

Email token validation

Account activation

Flash messages for success/failure

3. UserController
Base Path: /user

Purpose: Handles user dashboard and profile management

Endpoints:

text
GET  /user/dashboard                    → User dashboard (with contacts list)
GET  /user/profile                      → User profile page
POST /user/profile                      → Update profile
Key Methods:

dashboard() - Displays user dashboard with paginated contacts

userProfile() - User profile page

Features:

Dashboard with contact overview

Pagination support (page, size, sortBy, direction)

Sorting capabilities

4. ContactController
Base Path: /user/contacts

Purpose: Core contact management functionality

Endpoints:

text
GET  /user/contacts/add                 → Add contact form
POST /user/contacts/add                 → Save new contact
GET  /user/contacts                     → List all contacts (paginated)
GET  /user/contacts/search              → Search contacts
DELETE /user/contacts/delete/{contactId} → Delete contact
GET  /user/contacts/view/{contactId}    → View/Edit contact form
POST /user/contacts/update/{contactId}  → Update contact
Key Methods:

addContactView() - Display add contact form

saveContact() - Validate and save contact with image upload

viewContact() - List contacts with pagination/sorting

searchHandler() - Search by name, email, or phone

deleteContact() - Delete contact with cascade

uppdateContactFormView() - Populate edit form

updateContact() - Update contact information

Features:

Image upload via Cloudinary

Full-text search on name, email, phone

Pagination with configurable page size

Sorting by multiple fields

Input validation with BindingResult

Session-based flash messages

5. ApiController
Base Path: /api

Purpose: RESTful API endpoints for programmatic access

Endpoints:

text
GET /api/contacts/{contactId}  → Return contact as JSON
Key Methods:

getContact() - Fetch contact by ID (REST response)

Response Format: JSON Contact object

6. RootController (@ControllerAdvice)
Purpose: Global controller advice for all requests

Methods:

addLoggedInUserInformation() - Populates Model with logged-in user for all views

Features:

Automatic user injection into all views

Accessible in templates as ${loggedinuser}

Data Models & Entities
User Entity
java
@Entity(name="user")
@Table(name="users")
public class User implements UserDetails {
    @Id
    private String userId;                          // UUID
    
    @Column(name="user_name", nullable=false)
    private String name;                            // User's name
    
    @Column(nullable=false, unique=true)
    private String email;                           // Email (login)
    
    private String password;                        // Encrypted password
    
    @Column(columnDefinition="TEXT")
    private String about;                           // Biography
    
    @Column(columnDefinition="TEXT")
    private String profilepic;                      // Profile picture URL
    
    private String phoneNumber;                     // Phone
    
    private boolean enabled;                        // Account enabled
    
    private boolean emailverified;                  // Email verified
    
    private boolean phoneverified;                  // Phone verified
    
    @Enumerated(EnumType.STRING)
    private Providers provider;                     // SELF, GOOGLE, GITHUB
    
    private String provideruserId;                  // External provider ID
    
    @OneToMany(mappedBy="user", cascade=CascadeType.ALL, 
               fetch=FetchType.LAZY, orphanRemoval=true)
    private List<Contact> contact;                  // User's contacts
    
    @ElementCollection(fetch=FetchType.EAGER)
    private List<String> roleList;                  // User roles
    
    private String emailToken;                      // Email verification token
    
    // UserDetails implementation methods for Spring Security
}
Key Features:

Implements UserDetails interface for Spring Security integration

UUID-based primary key

Cascade delete for associated contacts

Enum-based provider support for OAuth

Collection-based role management

Contact Entity
java
@Entity
public class Contact {
    @Id
    private String contactId;                       // UUID
    
    private String name;                            // Contact name
    
    private String email;                           // Contact email
    
    private String phoneNumber;                     // Contact phone
    
    private String address;                         // Contact address
    
    private String picture;                         // Picture URL
    
    @Column(length=10000)
    private String description;                     // Long description
    
    private boolean favourite;                      // Favorite flag
    
    private String linkedInLink;                    // LinkedIn URL
    
    private String website;                         // Website URL
    
    private String cloudinaryPicPublicId;           // Cloudinary ID for deletion
    
    @ManyToOne
    @JsonIgnore
    private User user;                              // Parent user
    
    @OneToMany(mappedBy="contact", cascade=CascadeType.ALL,
               fetch=FetchType.EAGER, orphanRemoval=true)
    private List<Social_links> links;               // Social media links
}
Key Features:

Bidirectional relationship with User (Many-to-One)

One-to-Many relationship with Social_links

Cloudinary integration for image management

Cascade operations (delete contact = delete links)

Large description field (10000 characters)

Social_links Entity
java
@Entity
public class Social_links {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;                                // Auto-generated ID
    
    private String link;                            // Social media link URL
    
    private String title;                           // Link title/platform
    
    @ManyToOne
    private Contact contact;                        // Parent contact
}
Key Features:

Simple link management

Many-to-One relationship with Contact

Auto-increment ID generation

Supports multiple links per contact

Providers Enum
java
public enum Providers {
    SELF,       // Local registration
    GOOGLE,     // Google OAuth
    GITHUB      // GitHub OAuth
}
Form/DTO Classes
UserForm (DTO)
text
- name: String
- email: String
- phoneNumber: String
- password: String
- confirmPassword: String
- about: String
ContactForm (DTO)
text
- name: String
- email: String
- phoneNumber: String
- address: String
- description: String
- linkedInlink: String
- website: String
- picture: String
- contactImage: MultipartFile (for upload)
- favourite: boolean
ContactSearchForm (DTO)
text
- field: String (name/email/phone)
- value: String (search query)
Installation & Setup
Prerequisites
Java 17+ - JDK installed and JAVA_HOME configured

Maven 3.6+ - Build tool

MySQL 8.0+ or PostgreSQL 12+ - Database

Git - Version control

IDE - IntelliJ IDEA or VS Code with Java extensions

Cloudinary Account - For image storage (free tier available)

Step 1: Clone Repository
bash
git clone https://github.com/ayush09-bit/SCM.git
cd SCM
Step 2: Database Setup
For MySQL:
sql
CREATE DATABASE scm_database;
USE scm_database;
For PostgreSQL:
sql
CREATE DATABASE scm_database;
Step 3: Configure Application Properties
Edit src/main/resources/application.properties:

text
# Server Configuration
server.port=8080
server.servlet.context-path=/

# Database Configuration (MySQL)
spring.datasource.url=jdbc:mysql://localhost:3306/scm_database
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true

# Application Title
spring.application.name=Smart_Contact_Manager

# Email Configuration (Optional)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# OAuth Configuration (Optional)
# Google OAuth
spring.security.oauth2.client.registration.google.client-id=your_google_client_id
spring.security.oauth2.client.registration.google.client-secret=your_google_client_secret

# GitHub OAuth
spring.security.oauth2.client.registration.github.client-id=your_github_client_id
spring.security.oauth2.client.registration.github.client-secret=your_github_client_secret

# Cloudinary Configuration
cloudinary.cloud.name=your_cloud_name
cloudinary.api.key=your_api_key
cloudinary.api.secret=your_api_secret

# Logging
logging.level.root=INFO
logging.level.com.Smart_Contact_Manager.demo=DEBUG
Step 4: Install Dependencies
bash
mvn clean install
Step 5: Build the Application
bash
mvn clean package
Running the Application
Option 1: Maven Command Line
bash
# Development mode
mvn spring-boot:run

# With custom port
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9090"
Option 2: IDE (IntelliJ IDEA)
Open project in IntelliJ

Run → Run 'DemoApplication'

Application starts on configured port (default: 8080)

Option 3: JAR File Execution
bash
# Build JAR
mvn clean package

# Run JAR
java -jar target/SCM.jar

# Run JAR with environment variables
java -Dspring.datasource.url=jdbc:mysql://localhost:3306/scm \
     -Dspring.datasource.username=root \
     -Dspring.datasource.password=password \
     -jar target/SCM.jar
Option 4: Docker
bash
# Build Docker image
docker build -t scm-app .

# Run container
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/scm \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=password \
  scm-app

# Using Docker Compose
docker-compose up --build
Access the Application
Once running, open your browser and navigate to:

text
http://localhost:8080
Project Flow & User Journey
1. Registration Flow
text
User visits: / or /home
              ↓
User clicks "Register" → /register
              ↓
PageController.signuppage() returns register.html with UserForm model
              ↓
User fills form (name, email, phone, password, about)
              ↓
Form submitted → POST /do-register
              ↓
PageController.processRegister() validates UserForm
              ↓
UserServicesImpl.saveUser()
  - Hash password with BCryptPasswordEncoder
  - Generate UUID for userId
  - Generate emailToken
  - Set enabled=false, emailverified=false
  - Save to database
              ↓
EmailService.sendVerificationEmail()
  - Sends verification link: /auth/verify-email?token={emailToken}
              ↓
User receives email, clicks verification link
              ↓
AuthController.verifyEmail() validates token
  - Sets emailverified=true, enabled=true
  - Saves user
              ↓
Redirect to /login with success message
2. Login Flow
text
User visits: /login
              ↓
Spring Security (SecurityConfig) processes login
              ↓
SecurityCustomerUserDetailService loads user by email
  - Validates password with BCryptPasswordEncoder
  - Loads authorities/roles
              ↓
Authentication succeeds
              ↓
Redirect to /user/dashboard
              ↓
RootController injects logged-in user into model
  - Available as ${loggedinuser} in templates
3. Contact Management Flow
text
Authenticated User visits: /user/contacts
              ↓
ContactController.viewContact()
  - Gets logged-in user from Authentication object
  - Calls contactService.getByUser(user, page, size, sortBy, direction)
  - Returns paginated Page<Contact>
              ↓
Displays user/contacts.html with contact list
              ↓
User clicks "Add Contact" → /user/contacts/add
              ↓
ContactController.addContactView() returns form
              ↓
User fills ContactForm and uploads image
              ↓
POST /user/contacts/add
              ↓
ContactController.saveContact()
  - Validates ContactForm with BindingResult
  - If errors, returns form with validation messages
  - Generates UUID for contactId
  - Uploads image to Cloudinary via imageService
  - Sets contact.user = logged-in user
  - Saves contact via contactService.save()
              ↓
Session flash message: "Contact added successfully"
              ↓
Redirect to /user/contacts/add
4. Search Flow
text
User enters search criteria (field, value)
              ↓
POST /user/contacts/search
              ↓
ContactController.searchHandler()
  - Validates field (name/email/phone)
  - Calls appropriate service method:
    * contactService.searchByName()
    * contactService.searchByEmail()
    * contactService.searchByPhoneNumber()
  - Returns filtered, paginated results
              ↓
Displays user/search.html with results
5. Logout Flow
text
User clicks "Logout"
              ↓
Spring Security handles logout
  - Invalidates session
  - Clears authentication
              ↓
Redirect to /login
Development Best Practices
Code Organization
Separation of Concerns

Controllers handle HTTP requests/responses

Services contain business logic

Repositories handle database operations

Entities define domain models

Dependency Injection

Use @Autowired annotation

Inject interfaces, not implementations

Example: @Autowired private UserServices userService;

Exception Handling

Use custom exceptions (ResourceNotFoundException)

Handle at service layer

Return meaningful error messages

Security

Spring Security handles authentication/authorization

Passwords encrypted with BCryptPasswordEncoder

CSRF protection enabled by default

OAuth support for social login

Email verification tokens for account activation

Validation

Use Jakarta Validation annotations

Custom validators for complex rules

@ValidFile for file upload validation

BindingResult for error handling in controllers

Logging

Use SLF4J with Logback

Log at INFO level for business operations

DEBUG level for detailed tracing

Example: logger.info("User {} logged in", email);

Database Best Practices

Use Spring Data JPA for data access

Leverage Hibernate for ORM

Use pagination for large result sets

Create indexes for frequently searched columns

Use cascade operations appropriately

Frontend Best Practices

Use Thymeleaf for templating

Leverage Tailwind CSS for styling

Organize CSS with utility classes

Minimize inline JavaScript

Use HTML5 semantic elements

Testing
bash
# Run tests
mvn test

# Run with coverage
mvn clean test jacoco:report

# View coverage report
# open target/site/jacoco/index.html
Performance Optimization
Database Queries

Use pagination for large datasets

Create appropriate indexes

Use fetch = FetchType.LAZY for large collections

Use fetch = FetchType.EAGER carefully

Caching

Consider @Cacheable for frequently accessed data

Spring Cache abstraction with Caffeine or Redis

Image Optimization

Use Cloudinary for storage and optimization

Cloudinary automatically optimizes images

Lazy load images in frontend

Database Connection Pooling

HikariCP is default in Spring Boot

Configure pool size based on load

Contributing
Guidelines
Fork the repository

Create a feature branch - git checkout -b feature/amazing-feature

Commit changes - git commit -m 'Add amazing feature'

Push to branch - git push origin feature/amazing-feature

Open a Pull Request with clear description

Code Standards
Follow Java naming conventions

Use meaningful variable/method names

Add comments for complex logic

Keep methods small and focused

Write unit tests for new features

Future Enhancements
 Export contacts as CSV/Excel

 Bulk import contacts

 Contact groups/categories

 Advanced filters and tags

 Integration with external APIs (Google Contacts)

 Email reminders/notifications

 Mobile app (React Native/Flutter)

 Real-time collaboration

 Contact activity timeline

 Multi-language support

 Dark mode UI theme

 Two-factor authentication (2FA)

Troubleshooting
Common Issues
Issue: Database connection error

text
Solution: 
- Verify MySQL is running: systemctl status mysql
- Check credentials in application.properties
- Verify database exists: CREATE DATABASE scm_database;
Issue: Image upload fails

text
Solution:
- Verify Cloudinary credentials in application.properties
- Check file size limits
- Ensure file format is supported (jpg, png, gif)
Issue: Email verification not working

text
Solution:
- Verify SMTP settings in application.properties
- Check app password (not regular password for Gmail)
- Verify firewall allows outbound SMTP
Issue: OAuth login not working

text
Solution:
- Verify OAuth client credentials
- Check redirect URI configuration in OAuth provider
- Verify Spring Security OAuth2 configuration
License
This project is licensed under the MIT License - see the LICENSE file for details.

Support & Contact
For issues, questions, or suggestions:

GitHub Issues: Report issues on GitHub

Email: your_email@example.com

Documentation: Check Wiki for detailed guides

Acknowledgments
Spring Boot documentation

Spring Security guides

Thymeleaf template documentation

Tailwind CSS documentation

Cloudinary API documentation

Last Updated: November 2025
Version: 1.0.0
Status: Active Development

Quick Reference: API Summary
Feature	Endpoint	Method	Auth Required
Home	/	GET	No
Register	/do-register	POST	No
Verify Email	/auth/verify-email	GET	No
Login	/login	POST	No
Dashboard	/user/dashboard	GET	Yes
Add Contact	/user/contacts/add	POST	Yes
List Contacts	/user/contacts	GET	Yes
Search Contacts	/user/contacts/search	GET	Yes
Update Contact	/user/contacts/update/{id}	POST	Yes
Delete Contact	/user/contacts/delete/{id}	GET	Yes
Get Contact API	/api/contacts/{id}	GET	Yes
User Profile	/user/profile	GET	Yes
