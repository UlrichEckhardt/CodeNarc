/*
 * Copyright 2009 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codenarc.rule.basic

import org.codenarc.rule.AbstractAstVisitor
import org.codenarc.rule.AbstractAstVisitorRule
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codenarc.util.AstUtil
import org.codehaus.groovy.ast.expr.ConstantExpression

/**
 * Finds empty string literals which are being added. This is an inefficient way to convert any type to a String.
 *
 * @author Hamlet D'Arcy
 * @version $Revision: 24 $ - $Date: 2009-01-31 13:47:09 +0100 (Sat, 31 Jan 2009) $
 */
class AddEmptyStringRule extends AbstractAstVisitorRule {
    String name = 'AddEmptyString'
    int priority = 2
    Class astVisitorClass = AddEmptyStringAstVisitor
}

class AddEmptyStringAstVisitor extends AbstractAstVisitor {
    @Override
    void visitBinaryExpression(BinaryExpression expression) {

        if (AstUtil.isBinaryExpressionType(expression, '+')) {
            if (expression.leftExpression instanceof ConstantExpression && expression.leftExpression.value == "") {
                addViolation expression, 'Concatenating an empty string is an inefficient way to convert an object to a String. Consider using toString() or String.valueOf(Object)'
            }
        }
        
        super.visitBinaryExpression(expression)
    }
}
