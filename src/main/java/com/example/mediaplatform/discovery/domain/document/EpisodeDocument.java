package com.example.mediaplatform.discovery.domain.document;

import java.time.Instant;
import java.util.List;

public class EpisodeDocument {
    private Long episodeId;
    private Long programId;
    private String programTitle;
    private String episodeTitle;
    private String description;
    private String type;
    private String language;
    private Integer durationSeconds;
    private Instant publishDate;
    private String slug;
    private List<String> categories;

    public Long getEpisodeId() { return episodeId; }
    public void setEpisodeId(Long episodeId) { this.episodeId = episodeId; }
    public Long getProgramId() { return programId; }
    public void setProgramId(Long programId) { this.programId = programId; }
    public String getProgramTitle() { return programTitle; }
    public void setProgramTitle(String programTitle) { this.programTitle = programTitle; }
    public String getEpisodeTitle() { return episodeTitle; }
    public void setEpisodeTitle(String episodeTitle) { this.episodeTitle = episodeTitle; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public Integer getDurationSeconds() { return durationSeconds; }
    public void setDurationSeconds(Integer durationSeconds) { this.durationSeconds = durationSeconds; }
    public Instant getPublishDate() { return publishDate; }
    public void setPublishDate(Instant publishDate) { this.publishDate = publishDate; }
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }
}
