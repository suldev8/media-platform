INSERT INTO categories (id, name, slug) VALUES
    (1, 'أعمال', 'business'),
    (2, 'تقنية', 'technology'),
    (3, 'ثقافة', 'culture'),
    (4, 'مجتمع', 'society'),
    (5, 'وثائقي', 'documentary')
ON CONFLICT (id) DO NOTHING;

SELECT setval('categories_id_seq', (SELECT MAX(id) FROM categories));
