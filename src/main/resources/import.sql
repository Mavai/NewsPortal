INSERT INTO Category (Name) VALUES ('Science')
INSERT INTO Category (Name) VALUES ('Technology')
INSERT INTO Category (Name) VALUES ('Sports')
INSERT INTO Category (Name) VALUES ('Politics')
INSERT INTO Category (Name) VALUES ('Economy')

INSERT INTO News (Title, Lead, Content, Publish_Time) VALUES ('Fake News 1', 'Lead', 'Content', CURRENT_DATE)
INSERT INTO News (Title, Lead, Content, Publish_Time) VALUES ('Fake News 2', 'Lead', 'Content', CURRENT_DATE)
INSERT INTO News (Title, Lead, Content, Publish_Time) VALUES ('Fake News 3', 'Lead', 'Content', CURRENT_DATE)
INSERT INTO News (Title, Lead, Content, Publish_Time) VALUES ('Fake News 4', 'Lead', 'Content', CURRENT_DATE)
INSERT INTO News (Title, Lead, Content, Publish_Time) VALUES ('Fake News 5', 'Lead', 'Content', CURRENT_DATE)

INSERT INTO Category_NEWS_ITEMS (Categories_Id, News_Items_Id) VALUES (1, 1)
INSERT INTO Category_News_Items (Categories_Id, News_Items_Id) VALUES (2, 2)
INSERT INTO Category_News_Items (Categories_Id, News_Items_Id) VALUES (3, 3)
INSERT INTO Category_News_Items (Categories_Id, News_Items_Id) VALUES (4, 4)
INSERT INTO Category_News_Items (Categories_Id, News_Items_Id) VALUES (5, 5)