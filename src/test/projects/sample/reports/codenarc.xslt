<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes"/>
    <xsl:decimal-format decimal-separator="." grouping-separator=","/>

    <xsl:key name="violations" match="Violation" use="@ruleName"/>

    <xsl:template match="CodeNarc">
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
                            <dd><a href="{@url}">CodeNarc</a></dd>
                        </dl>
                        <dl>
                            <dt>Version</dt>
                            <dd><xsl:value-of select="@version"/></dd>
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

                <xsl:apply-templates select="." mode="summary"/>

                <div class="rule"></div>

                <xsl:apply-templates select="." mode="full"/>

                <div class="rule"></div>

                <xsl:apply-templates select="Rules"/>

                <div class="rule"></div>

            </body>
        </html>
    </xsl:template>

    <xsl:template match="PackageSummary">
        <ul>
            <li>
                <strong>
                    <xsl:value-of select="@totalFiles"/>
                </strong>
                <span>total files</span>
            </li>
            <li>
                <strong>
                    <xsl:value-of select="@filesWithViolations"/>
                </strong>
                <span>file violations</span>
            </li>
            <li>
                <strong>
                    <xsl:value-of select="@priority1"/>
                </strong>
                <span>priority 1</span>
            </li>
            <li>
                <strong>
                    <xsl:value-of select="@priority2"/>
                </strong>
                <span>priority 2</span>
            </li>
            <li>
                <strong>
                    <xsl:value-of select="@priority3"/>
                </strong>
                <span>priority 3</span>
            </li>
        </ul>
    </xsl:template>

    <xsl:template match="Rules">
        <h2>Rules</h2>

        <table border="0" width="100%" cellpadding="0" cellspacing="0">
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Violations</th>
        </tr>

        <xsl:apply-templates select="Rule"/>

        </table>
    </xsl:template>

    <xsl:template match="Rule">
        <xsl:variable name="ruleName" select="@name"/>
        <xsl:variable name="violationCount" select="count(key('violations', $ruleName))"/>
        <xsl:if test="$violationCount > 0">
        <tr>
            <td>
                <a name="r-{$ruleName}"> </a>
                <xsl:value-of select="$ruleName"/>
            </td>
            <td>
                <xsl:value-of select="Description"/>
            </td>
            <td>
                <xsl:value-of select="$violationCount"/>
            </td>
        </tr>
        </xsl:if>
    </xsl:template>

    <xsl:template match="CodeNarc" mode="summary">
        <h2>Files</h2>

        <table border="0" width="100%" cellpadding="0" cellspacing="0">
        <tr>
            <th>Name</th>
            <th>Violations</th>
        </tr>
        <xsl:for-each select="Package/File">
            <xsl:sort select="@name"/>
            <xsl:apply-templates select="." mode="summary"/>
        </xsl:for-each>
        </table>
    </xsl:template>

    <xsl:template match="CodeNarc" mode="full">
        <xsl:for-each select="Package/File">
            <xsl:sort select="@name"/>
            <xsl:apply-templates select="." mode="full"/>
            <p/>
            <p/>
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="File" mode="summary">
        <xsl:variable name="violationCount" select="count(Violation)"/>
        <tr>
            <td><a href="#f-{@name}"><xsl:value-of select="../@path"/>/<xsl:value-of select="@name"/></a></td>
            <td><xsl:value-of select="$violationCount"/></td>
        </tr>
    </xsl:template>

    <xsl:template match="File" mode="full">
        <a name="f-{@name}"></a>
        <h3>
            File
            <xsl:value-of select="@name"/>
        </h3>

        <table border="0" width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <th>Priority</th>
                <th>Rule</th>
                <th>Violation Description</th>
                <th>Line</th>
                <th>Source</th>
            </tr>
            <xsl:for-each select="Violation">
                <xsl:sort select="@priority"/>
                <tr>
                    <td>
                        <xsl:value-of select="@priority"/>
                    </td>
                    <td>
                        <a href="#r-{@ruleName}">
                            <xsl:value-of select="@ruleName"/>
                        </a>
                    </td>
                    <td>
                        <xsl:value-of select="Message"/>
                    </td>
                    <td>
                        <xsl:value-of select="@lineNumber"/>
                    </td>
                    <td>
                        <code>
                            <xsl:value-of select="SourceLine"/>
                        </code>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
        <a href="#top">Back to top</a>
    </xsl:template>

</xsl:stylesheet>