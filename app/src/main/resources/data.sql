INSERT INTO ingredients (id, ingredient, created_at, updated_at) VALUES (101,'Tomato', now(), now());
INSERT INTO ingredients (id, ingredient, created_at, updated_at) VALUES (102,'Onion', now(), now());
INSERT INTO ingredients (id, ingredient, created_at, updated_at) VALUES (103,'Garlic', now(), now());
INSERT INTO ingredients (id, ingredient, created_at, updated_at) VALUES (104,'Pasta', now(), now());
INSERT INTO recipes (id, name, instructions, type, number_of_servings, created_at, updated_at, ingredient_id) VALUES (100,'Spaghetti', 'spicy', 'VEGETERIEN', 2, now(), now(), 101);