package com.example.dgs.configuration

import com.example.dgs.getProductOperationName
import com.netflix.graphql.dgs.client.codegen.*
import graphql.language.*
import graphql.schema.Coercing

class ProductGraphQLQueryRequest(
    val query: GraphQLQuery,
    val projection: BaseProjectionNode?,
    scalars: Map<Class<*>, Coercing<*, *>>?
) {
    constructor(query: GraphQLQuery) : this(query, null, null)
    constructor(query: GraphQLQuery, projection: BaseProjectionNode?) : this(query, projection, null)

    val inputValueSerializer = InputValueSerializer(scalars ?: emptyMap())
    val projectionSerializer = ProjectionSerializer(inputValueSerializer)

    fun serialize(): String {
        val operationDef = OperationDefinition.newOperationDefinition()

        query.name?.let { operationDef.name(it) } ?: operationDef.name(query.getProductOperationName())
        query.getOperationType()?.let { operationDef.operation(OperationDefinition.Operation.valueOf(it.uppercase())) }

        if (query.variableDefinitions.isNotEmpty()) {
            operationDef.variableDefinitions(query.variableDefinitions)
        }

        val selection = Field.newField(query.getOperationName())
        if (query.input.isNotEmpty()) {
            selection.arguments(
                query.input.map { (name, value) ->
                    Argument(name, inputValueSerializer.toValue(value))
                }
            )
        }

        if (projection != null) {
            val selectionSet = if (projection is BaseSubProjectionNode<*, *>) {
                projectionSerializer.toSelectionSet(projection.root() as BaseProjectionNode)
            } else {
                projectionSerializer.toSelectionSet(projection)
            }
            if (selectionSet.selections.isNotEmpty()) {
                selection.selectionSet(selectionSet)
            }
        }

        operationDef.selectionSet(SelectionSet.newSelectionSet().selection(selection.build()).build())

        return AstPrinter.printAst(operationDef.build())
    }
}