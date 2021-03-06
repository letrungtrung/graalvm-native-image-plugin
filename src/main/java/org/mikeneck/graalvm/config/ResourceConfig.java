/*
 * Copyright 2020 Shinya Mochida
 *
 * Licensed under the Apache License,Version2.0(the"License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,software
 * Distributed under the License is distributed on an"AS IS"BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mikeneck.graalvm.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

public class ResourceConfig implements MergeableConfig<ResourceConfig> {

    @NotNull
    public List<ResourceUsage> resources = Collections.emptyList();

    public void setResources(@NotNull List<ResourceUsage> resources) {
        SortedSet<ResourceUsage> newResources = new TreeSet<>(resources);
        this.resources = Collections.unmodifiableList(new ArrayList<>(newResources));
    }

    @NotNull
    public List<BundleUsage> bundles = Collections.emptyList();

    public void setBundles(@NotNull List<BundleUsage> bundles) {
        SortedSet<BundleUsage> newBundles = new TreeSet<>(bundles);
        this.bundles = Collections.unmodifiableList(new ArrayList<>(newBundles));
    }

    public ResourceConfig() {
    }

    @TestOnly
    ResourceConfig(@NotNull List<String> resources, @NotNull String... bundles) {
        this(resources.stream()
                        .map(ResourceUsage::new)
                        .collect(Collectors.toList()),
                Arrays.stream(bundles)
                        .map(BundleUsage::new)
                        .collect(Collectors.toList()));
    }

    public ResourceConfig(@NotNull List<ResourceUsage> resources, @NotNull List<BundleUsage> bundles) {
        SortedSet<ResourceUsage> newResources = new TreeSet<>(resources);
        this.resources = Collections.unmodifiableList(new ArrayList<>(newResources));
        SortedSet<BundleUsage> newBundles = new TreeSet<>(bundles);
        this.bundles = Collections.unmodifiableList(new ArrayList<>(newBundles));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResourceConfig)) return false;
        ResourceConfig that = (ResourceConfig) o;
        return resources.equals(that.resources) &&
                bundles.equals(that.bundles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resources, bundles);
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResourceUsage{");
        sb.append("resources=").append(resources);
        sb.append(", bundles=").append(bundles);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public ResourceConfig mergeWith(ResourceConfig other) {
        Set<ResourceUsage> resources = new TreeSet<>();
        resources.addAll(this.resources);
        resources.addAll(other.resources);
        Set<BundleUsage> bundles = new TreeSet<>();
        bundles.addAll(this.bundles);
        bundles.addAll(other.bundles);
        return new ResourceConfig(
                Collections.unmodifiableList(new ArrayList<>(resources)),
                Collections.unmodifiableList(new ArrayList<>(bundles)));
    }
}
