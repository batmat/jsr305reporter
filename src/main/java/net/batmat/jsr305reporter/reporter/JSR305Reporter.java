package net.batmat.jsr305reporter.reporter;

import org.aspectj.lang.JoinPoint;

public interface JSR305Reporter
{
	void reportIncorrectNullParameter(JoinPoint joinPoint, int parameterIndex);
}