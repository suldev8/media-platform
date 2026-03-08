INSERT INTO programs (id, title, description, type, language, slug, status, created_at, updated_at) VALUES
    (1001, 'فنجان', 'بودكاست حواري طويل من ثمانية', 'PODCAST', 'ar', 'finjan', 'DRAFT', NOW(), NOW()),
    (1002, 'سردة', 'برنامج وثائقي وقصصي', 'DOCUMENTARY', 'ar', 'sarda', 'DRAFT', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO episodes (id, program_id, title, description, duration_seconds, language, publish_date, slug, status, version, created_at, updated_at) VALUES
    (2001, 1001, 'كيف تبني عادة قوية؟', 'حلقة تجريبية مضافة كبذرة أولية لاختبار APIs وDiscovery.', 3600, 'ar', null, 'how-to-build-habits', 'DRAFT', 0, NOW(), NOW()),
    (2002, 1001, 'لماذا تفشل المنتجات؟', 'حلقة ثانية لتجربة البحث والتصفية.', 4200, 'ar', null, 'why-products-fail', 'DRAFT', 0, NOW(), NOW()),
    (2003, 1002, 'رحلة في تاريخ الجزيرة العربية', 'حلقة وثائقية أولية لاختبار Discovery.', 2700, 'ar', null, 'history-of-arabia', 'DRAFT', 0, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO episode_categories (episode_id, category_id) VALUES
    (2001, 1),
    (2001, 4),
    (2002, 1),
    (2002, 2),
    (2003, 3),
    (2003, 5)
ON CONFLICT DO NOTHING;

INSERT INTO import_jobs (id, provider, status, payload, started_at, finished_at) VALUES
    (3001, 'youtube', 'COMPLETED', '{"channel":"thmanyah"}', NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day')
ON CONFLICT (id) DO NOTHING;

SELECT setval('programs_id_seq', (SELECT MAX(id) FROM programs));
SELECT setval('episodes_id_seq', (SELECT MAX(id) FROM episodes));
SELECT setval('import_jobs_id_seq', (SELECT MAX(id) FROM import_jobs));
