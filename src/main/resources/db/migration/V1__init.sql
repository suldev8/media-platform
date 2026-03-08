CREATE TABLE programs (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    type VARCHAR(50) NOT NULL,
    language VARCHAR(20) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    slug VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE episodes (
    id BIGSERIAL PRIMARY KEY,
    program_id BIGINT NOT NULL REFERENCES programs(id),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    duration_seconds INTEGER NOT NULL,
    language VARCHAR(20) NOT NULL,
    publish_date TIMESTAMP WITH TIME ZONE,
    slug VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,
    version BIGINT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE episode_categories (
    episode_id BIGINT NOT NULL REFERENCES episodes(id),
    category_id BIGINT NOT NULL REFERENCES categories(id),
    PRIMARY KEY (episode_id, category_id)
);

CREATE TABLE import_jobs (
    id BIGSERIAL PRIMARY KEY,
    provider VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL,
    payload TEXT,
    started_at TIMESTAMP WITH TIME ZONE NOT NULL,
    finished_at TIMESTAMP WITH TIME ZONE
);

CREATE INDEX idx_episodes_program_id ON episodes(program_id);
CREATE INDEX idx_episodes_status_publish_date ON episodes(status, publish_date DESC);
CREATE INDEX idx_episode_categories_category_episode ON episode_categories(category_id, episode_id);
