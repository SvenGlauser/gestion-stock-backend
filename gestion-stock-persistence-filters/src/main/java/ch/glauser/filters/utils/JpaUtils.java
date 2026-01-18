package ch.glauser.filters.utils;

import jakarta.persistence.criteria.Path;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class JpaUtils {
    /**
     * Récupère le Path JPA en fonction du nom du champ (supporte les champs imbriqués avec des '.')
     *
     * @param path Chemin racine
     * @param fieldName Nom du champ (en cascade avec des '.')
     *
     * @return Le chemin JPA calculé ou racine si le nom du champ est vide
     */
    public static Path<?> getPath(Path<?> path, String fieldName) {
        if (StringUtils.isBlank(fieldName)) {
            return path;
        }

        List<String> fields = Arrays
                .stream(fieldName.split("\\."))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toCollection(LinkedList::new));

        Path<?> currentPath = path;

        for (String field : fields) {
            currentPath = currentPath.get(field);
        }

        return currentPath;
    }
}
