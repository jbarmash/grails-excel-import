<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes"/>
    <xsl:decimal-format decimal-separator="." grouping-separator=","/>

    <xsl:key name="packageSummary" match="PackageSummary/MetricResult" use="@name"/>

    <xsl:template match="GMetrics">
        <html>
            <head>
                <title><xsl:value-of select="Project/@title"/></title>
                <link rel="stylesheet" href="default.css" type="text/css"/>
            </head>
            <body>
                <a name="top"></a>

                <div class="header">
                    <h1>
                        <img src="logo.gif" width="44" height="37"/>
                        <xsl:value-of select="Project/@title"/>
                     </h1>
                </div>

                <div class="rule"></div>

                <div class="summary">
                    <div class="first">
                        <dl>
                            <dt>Generated</dt>
                            <dd><xsl:value-of select="Report/@timestamp"/></dd>
                        </dl>
                        <dl>
                            <dt>Tool</dt>
                            <dd><a href="{@url}"><xsl:value-of select="@version"/></a></dd>
                        </dl>

                        <dl>
                            <dt>Sources</dt>
                            <dl>
                            <xsl:for-each select="Project/SourceDirectory">
                                <xsl:value-of select="."/>
                                

                            </xsl:for-each>
                            </dl>
                        </dl>
                    </div>
                    <div class="last">
                        <xsl:apply-templates select="PackageSummary"/>
                    </div>
                </div>

                <div class="rule"></div>

                <div id="top-lists">
                    <div class="first">
                        <xsl:apply-templates select="." mode="top-classes"/>
                    </div>
                    <div class="last">
                        <xsl:apply-templates select="." mode="top-methods"/>
                    </div>
                </div>

                <div class="rule"></div>

                <xsl:apply-templates select="." mode="full"/>

                <div class="rule"></div>

                <xsl:apply-templates select="Metrics"/>

                <div class="rule"></div>

            </body>
        </html>
    </xsl:template>

    <xsl:template match="PackageSummary">
        <ul id="summary_stats">
            <li>
                <strong>
                    <xsl:call-template name="display_value">
                        <xsl:with-param name="value" select="MetricResult[@name = 'ABC']/@average"/>
                    </xsl:call-template>
                </strong>
                <span>ABC</span>
            </li>
            <li>
                <strong>
                    <xsl:call-template name="display_value">
                        <xsl:with-param name="value" select="MetricResult[@name = 'CyclomaticComplexity']/@average"/>
                    </xsl:call-template>
                </strong>
                <span>cyclomatic complexity</span>
            </li>
            <li>
                <strong>
                    <xsl:call-template name="display_value">
                        <xsl:with-param name="value" select="MetricResult[@name = 'MethodLineCount']/@average"/>
                    </xsl:call-template>
                </strong>
                <span>method lines</span>
            </li>
            <li>
                <strong>
                    <xsl:call-template name="display_value">
                        <xsl:with-param name="value" select="MetricResult[@name = 'ClassLineCount']/@average"/>
                    </xsl:call-template>
                </strong>
                <span>class lines</span>
            </li>
        </ul>
    </xsl:template>

    <xsl:template match="Metrics">
        <h2>Metrics</h2>

        <table border="0" width="100%" cellpadding="0" cellspacing="0">
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Average</th>
            <th>Total</th>
            <th>Maximum</th>
            <th>Minimum</th>
        </tr>

        <xsl:apply-templates select="Metric"/>

        </table>
    </xsl:template>

    <xsl:template match="Metric">
        <xsl:variable name="metricName" select="@name"/>
        <xsl:variable name="summary" select="key('packageSummary', $metricName)"/>
        <tr>
            <td>
                <a name="m-{$metricName}"> </a>
                <xsl:value-of select="$metricName"/>
            </td>
            <td>
                <xsl:value-of select="Description"/>
            </td>
            <td class="number">
                <xsl:call-template name="display_value">
                    <xsl:with-param name="value" select="$summary/@average"/>
                </xsl:call-template>
            </td>
            <td class="number">
                <xsl:call-template name="display_value">
                    <xsl:with-param name="value" select="$summary/@total"/>
                </xsl:call-template>
            </td>
            <td class="number">
                <xsl:call-template name="display_value">
                    <xsl:with-param name="value" select="$summary/@maximum"/>
                </xsl:call-template>
            </td>
            <td class="number">
                <xsl:call-template name="display_value">
                    <xsl:with-param name="value" select="$summary/@minimum"/>
                </xsl:call-template>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="GMetrics" mode="top-methods">
        <h2>Top Complexity Methods</h2>

        <table border="0" cellpadding="0" cellspacing="0">
        <tr>
            <th>Name</th>
            <th>Complexity</th>
        </tr>
        <xsl:for-each select="//Method/MetricResult[@name = 'CyclomaticComplexity']">
            <xsl:sort select="@total" data-type="number" order="descending"/>
            <xsl:if test="position() &lt;= 7">
                <tr>
                    <td>
                        <a href="#method-{../../@name}-{../@name}">
                            <xsl:value-of select="../@name"/>
                        </a>
                    </td>
                    <td><xsl:value-of select="@total"/></td>
                </tr>
            </xsl:if>
        </xsl:for-each>
        </table>
    </xsl:template>

    <xsl:template match="GMetrics" mode="top-classes">
        <h2>Top Complexity Classes</h2>

        <table border="0" cellpadding="0" cellspacing="0">
        <tr>
            <th>Name</th>
            <th>Complexity</th>
        </tr>
        <xsl:for-each select="//Class/MetricResult[@name = 'CyclomaticComplexity']">
            <xsl:sort select="@total" data-type="number" order="descending"/>
            <xsl:if test="position() &lt;= 7">
                <tr>
                    <td>
                        <a href="#c-{../@name}">
                            <xsl:value-of select="../@name"/>
                        </a>
                    </td>
                    <td><xsl:value-of select="@total"/></td>
                </tr>
            </xsl:if>
        </xsl:for-each>
        </table>
    </xsl:template>

    <xsl:template match="GMetrics" mode="full">
        <xsl:for-each select="Package/Class">
            <xsl:sort select="@name"/>
            <xsl:apply-templates select="." mode="full"/>
            <p/>
            <p/>
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="Class" mode="summary">
        <xsl:variable name="violationCount" select="count(Violation)"/>
        <tr>
            <td><a href="#c-{@name}"><xsl:value-of select="../@path"/>/<xsl:value-of select="@name"/></a></td>
            <td><xsl:value-of select="$violationCount"/></td>
        </tr>
    </xsl:template>

    <xsl:template match="Class" mode="full">
        <xsl:variable name="classLineCount" select="MetricResult[@name = 'ClassLineCount']"/>
        <xsl:variable name="methodLineCount" select="MetricResult[@name = 'MethodLineCount']"/>
        <xsl:variable name="abc" select="MetricResult[@name = 'ABC']"/>
        <xsl:variable name="cyclomaticComplexity" select="MetricResult[@name = 'CyclomaticComplexity']"/>
        <a name="c-{@name}"></a>
        <h2>
            Class
            <xsl:value-of select="@name"/>
        </h2>

        <div class="class_summary">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tbody>
                    <tr>
                        <td>
                            <a href="#m-ClassLineCount">
                                <h3>Class line count</h3>
                            </a>
                            <p>
                                <strong>
                                    <xsl:call-template name="display_value">
                                        <xsl:with-param name="value" select="$classLineCount/@total"/>
                                        <xsl:with-param name="format" select="'####0'"/>
                                    </xsl:call-template>
                                </strong>
                            </p>
                        </td>
                        <td>
                            <a href="#m-MethodLineCount">
                                <h3>Methods</h3>
                            </a>
                            <p>
                                <strong>
                                    <xsl:value-of select="count(Method)"/>
                                </strong>
                            </p>
                            <dl>
                                <dt>Average LC:</dt>
                                <dd>
                                    <xsl:call-template name="display_value">
                                        <xsl:with-param name="value" select="$methodLineCount/@average"/>
                                    </xsl:call-template>
                                </dd>
                                <dt>Maximum LC:</dt>
                                <dd>
                                    <xsl:call-template name="display_value">
                                        <xsl:with-param name="value" select="$methodLineCount/@total"/>
                                    </xsl:call-template>
                                </dd>
                            </dl>
                        </td>
                        <td>
                            <a href="#m-CyclomaticComplexity">
                                <h3>Complexity</h3>
                            </a>
                            <p>
                                <strong>
                                    <xsl:call-template name="display_value">
                                        <xsl:with-param name="value" select="$cyclomaticComplexity/@average"/>
                                    </xsl:call-template>
                                </strong>
                            </p>
                            <dl>
                                <dt>Maximum:</dt>
                                <dd>
                                    <xsl:call-template name="display_value">
                                        <xsl:with-param name="value" select="$cyclomaticComplexity/@maximum"/>
                                    </xsl:call-template>
                                </dd>
                            </dl>
                        </td>
                        <td>
                            <a href="#m-ABC">
                                <h3>ABC</h3>
                            </a>
                            <p>
                                <strong>
                                    <xsl:call-template name="display_value">
                                        <xsl:with-param name="value" select="$abc/@average"/>
                                    </xsl:call-template>
                                </strong>
                            </p>
                            <dl>
                                <dt>Maximum:</dt>
                                <dd>
                                    <xsl:call-template name="display_value">
                                        <xsl:with-param name="value" select="$abc/@maximum"/>
                                    </xsl:call-template>
                                </dd>
                            </dl>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <xsl:if test="count(Method) &gt; 0">
        <table border="0" width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <th>Method</th>
                <th>Cyclomatic Complexity</th>
                <th>ABC</th>
                <th>Lines</th>
            </tr>
            <xsl:for-each select="Method">
                <xsl:sort select="MetricResult[@name = 'CyclomaticComplexity']/@total"/>
                <tr>
                    <td>
                        <a name="method-{../@name}-{@name}"></a>
                        <xsl:value-of select="@name"/>
                    </td>
                    <td>
                        <xsl:value-of select="MetricResult[@name = 'CyclomaticComplexity']/@total"/>
                    </td>
                    <td>
                        <xsl:value-of select="MetricResult[@name = 'ABC']/@total"/>
                    </td>
                    <td>
                        <xsl:value-of select="MetricResult[@name = 'MethodLineCount']/@total"/>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
        </xsl:if>
        <a href="#top">Back to top</a>
    </xsl:template>

    <xsl:template name="display_value">
        <xsl:param name="value"/>
        <xsl:param name="format" select="'####.0'"/>
        <xsl:choose>
            <xsl:when test="string($value) != ''">
                <xsl:value-of select="format-number($value, $format)"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="'N/A'"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>