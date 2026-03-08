package com.example.mediaplatform.discovery.infrastructure.repository;

import com.example.mediaplatform.discovery.domain.document.EpisodeDocument;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch.core.IndexRequest;
import org.opensearch.client.opensearch.core.IndexResponse;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.indices.CreateIndexRequest;
import org.opensearch.client.opensearch.indices.ExistsRequest;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EpisodeSearchRepository {

    private static final String INDEX = "episodes";

    private final OpenSearchClient client;

    public EpisodeSearchRepository(OpenSearchClient client) {
        this.client = client;
    }

    @PostConstruct
    public void ensureIndex() {
        try {
            boolean exists = client.indices().exists(new ExistsRequest.Builder().index(INDEX).build()).value();
            if (!exists) {
                client.indices().create(new CreateIndexRequest.Builder().index(INDEX).build());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize OpenSearch index", e);
        }
    }

    public void save(EpisodeDocument document) {
        try {
            IndexRequest<EpisodeDocument> request = new IndexRequest.Builder<EpisodeDocument>()
                    .index(INDEX)
                    .id(String.valueOf(document.getEpisodeId()))
                    .document(document)
                    .build();
            IndexResponse ignored = client.index(request);
        } catch (IOException e) {
            throw new RuntimeException("Failed to index episode", e);
        }
    }

    public Optional<EpisodeDocument> findBySlug(String slug) {
        try {
            SearchResponse<EpisodeDocument> response = client.search(s -> s
                            .index(INDEX)
                            .query(q -> q.matchPhrase(mp -> mp.field("slug").query(slug)))
                            .size(1),
                    EpisodeDocument.class);

            if (response.hits().hits().isEmpty() || response.hits().hits().get(0).source() == null) {
                return Optional.empty();
            }
            return Optional.of(response.hits().hits().get(0).source());
        } catch (IOException e) {
            throw new RuntimeException("Failed to query episode by slug", e);
        }
    }

    public List<EpisodeDocument> search(String q) {
        try {
            SearchResponse<EpisodeDocument> response = client.search(s -> s
                            .index(INDEX)
                            .query(query -> query.bool(b -> b
                                    .should(sh -> sh.match(m -> m.field("episodeTitle").query(FieldValue.of(q))))
                                    .should(sh -> sh.match(m -> m.field("description").query(FieldValue.of(q))))
                            )),
                    EpisodeDocument.class);

            List<EpisodeDocument> results = new ArrayList<>();
            response.hits().hits().forEach(hit -> {
                if (hit.source() != null) {
                    results.add(hit.source());
                }
            });
            return results;
        } catch (IOException e) {
            throw new RuntimeException("Failed to search episodes", e);
        }
    }
}
