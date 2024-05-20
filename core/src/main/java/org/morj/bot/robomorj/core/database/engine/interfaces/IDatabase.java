package org.morj.bot.robomorj.core.database.engine.interfaces;

import com.j256.ormlite.stmt.*;
import org.jooq.lambda.fi.util.function.CheckedConsumer;
import org.jooq.lambda.fi.util.function.CheckedUnaryOperator;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * @author TheDiVaZo
 * created on 08.05.2024
 */
public interface IDatabase<T, ID> extends IAsyncAutoCloseableDao<T, ID> {
    default CompletableFuture<T> getFirstElement(CheckedUnaryOperator<QueryBuilder<T, ID>> query) {
        return this.applyDaoQuery(dao -> (query.apply(dao.queryBuilder())).queryForFirst());
    }

    default CompletableFuture<Collection<T>> getElements(CheckedUnaryOperator<QueryBuilder<T, ID>> query) {
        return this.applyDaoQuery(dao -> (query.apply(dao.queryBuilder())).query());
    }

    default CompletableFuture<Integer> deleteElements(CheckedUnaryOperator<DeleteBuilder<T, ID>> delete) {
        return this.applyDaoQuery(dao -> (delete.apply(dao.deleteBuilder())).delete());
    }

    default CompletableFuture<Boolean> deleteElement(CheckedUnaryOperator<DeleteBuilder<T, ID>> delete) {
        return this.applyDaoQuery(dao -> (delete.apply(dao.deleteBuilder())).limit(1L).delete() == 1);
    }

    default CompletableFuture<Integer> updateElements(CheckedUnaryOperator<UpdateBuilder<T, ID>> update) {
        return this.applyDaoQuery(dao -> update.apply(dao.updateBuilder()).update());
    }

    default CompletableFuture<Boolean> updateFirstElement(CheckedUnaryOperator<UpdateBuilder<T, ID>> update) {
        return this.applyDaoQuery(dao -> (update.apply(dao.updateBuilder())).limit(1L).update() == 1);
    }

    default <Q extends StatementBuilder<T, ID>> CheckedUnaryOperator<Q> createStatementByID(ID id) {
        return builder -> {
            builder.where().idEq(id);
            return builder;
        };
    }

    default <Q extends StatementBuilder<T, ID>> CheckedUnaryOperator<Q> createStatementByRow(T element) {
        return builder -> {
            builder.where().idEq(this.idOf(element));
            return builder;
        };
    }

    default <Q extends StatementBuilder<T, ID>> CheckedUnaryOperator<Q> createWhereStatement(CheckedConsumer<Where<T, ID>> consumer) {
        return builder -> {
            consumer.accept(builder.where());
            return builder;
        };
    }

    default CompletableFuture<Void> createElements(Collection<T> collection) {
        return this.runDaoQuery(dao -> dao.create(collection));
    }

    default CompletableFuture<Void> createElement(T element) {
        return this.runDaoQuery(dao -> dao.create(element));
    }

    default CompletableFuture<Void> createOrUpdateElement(T element) {
        return this.runDaoQuery(dao -> dao.createOrUpdate(element));
    }

    default void initialize() {
        this.createTable().join();
    }

    ID idOf(T var1);
}
