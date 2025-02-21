package shoppingmall.domainredis.common.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;


@Component
public class DistributedLockKeyparser {
    private final ExpressionParser parser = new SpelExpressionParser();

    public String parseKey(String spelKey, ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature signature = (MethodSignature)proceedingJoinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = proceedingJoinPoint.getArgs();

        StandardEvaluationContext context = new StandardEvaluationContext();
        for(int i=0; i< parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }

        return parser.parseExpression(spelKey).getValue(context, String.class);


    }
}
