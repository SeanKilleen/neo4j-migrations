/*
 * Copyright 2020-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ac.simons.neo4j.migrations.core.catalog;

import static org.assertj.core.api.Assertions.assertThat;

import ac.simons.neo4j.migrations.core.internal.Neo4jEdition;
import ac.simons.neo4j.migrations.core.internal.Neo4jVersion;

import org.junit.jupiter.api.Test;

/**
 * @author Michael J. Simons
 */
class RenderConfigTest {

	@Test
	void ifNotExistsShouldWork() {

		RenderConfig ctx = RenderConfig.create().ifNotExists().forVersionAndEdition("4.0", "ENTERPRISE");
		assertThat(ctx.getOperator()).isEqualTo(Operator.CREATE);
		assertThat(ctx.isIdempotent()).isTrue();
		assertThat(ctx.getVersion()).isEqualTo(Neo4jVersion.V4_0);
		assertThat(ctx.getEdition()).isEqualTo(Neo4jEdition.ENTERPRISE);
	}

	@Test
	void ifExistsShouldWork() {

		RenderConfig ctx = RenderConfig.drop().ifExists().forVersionAndEdition("4.0", "ENTERPRISE");
		assertThat(ctx.getOperator()).isEqualTo(Operator.DROP);
		assertThat(ctx.isIdempotent()).isTrue();
		assertThat(ctx.getVersion()).isEqualTo(Neo4jVersion.V4_0);
		assertThat(ctx.getEdition()).isEqualTo(Neo4jEdition.ENTERPRISE);
	}

	@Test
	void nonIdempotentShouldBeOk() {

		RenderConfig ctx = RenderConfig.drop().forVersionAndEdition("4.0", "ENTERPRISE");
		assertThat(ctx.getOperator()).isEqualTo(Operator.DROP);
		assertThat(ctx.isIdempotent()).isFalse();
		assertThat(ctx.getVersion()).isEqualTo(Neo4jVersion.V4_0);
		assertThat(ctx.getEdition()).isEqualTo(Neo4jEdition.ENTERPRISE);
	}

	@Test
	void ignoreNameShouldWork() {

		RenderConfig ctx = RenderConfig.drop().forVersionAndEdition("4.0", "ENTERPRISE");
		RenderConfig ctx2 = ctx.ignoreName();

		assertThat(ctx2.getOperator()).isEqualTo(Operator.DROP);
		assertThat(ctx2.isIdempotent()).isFalse();
		assertThat(ctx2.getVersion()).isEqualTo(Neo4jVersion.V4_0);
		assertThat(ctx2.getEdition()).isEqualTo(Neo4jEdition.ENTERPRISE);

		assertThat(ctx.isIgnoreName()).isFalse();
		assertThat(ctx2.isIgnoreName()).isTrue();
	}
}
