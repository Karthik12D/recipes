CREATE TABLE ingredients (
    id bigint auto_increment PRIMARY KEY,
    ingredient VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE recipes (
    id bigint auto_increment PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    ingredient_id bigint,
    instructions TEXT NOT NULL,
    type VARCHAR(255) NOT NULL,
    number_of_servings INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ingredient_id) REFERENCES ingredients(id)
);