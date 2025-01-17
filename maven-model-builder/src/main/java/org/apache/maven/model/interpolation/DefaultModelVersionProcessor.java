package org.apache.maven.model.interpolation;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.util.Properties;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.maven.model.building.ModelBuildingRequest;

/**
 * Maven default implementation of the {@link ModelVersionProcessor} to support
 * <a href="https://maven.apache.org/maven-ci-friendly.html">CI Friendly Versions</a>
 */
@Named
@Singleton
public class DefaultModelVersionProcessor
    implements ModelVersionProcessor
{

    private static final String SHA1_PROPERTY = "sha1";

    private static final String CHANGELIST_PROPERTY = "changelist";

    private static final String REVISION_PROPERTY = "revision";

    @Override
    public boolean isValidProperty( String property )
    {
        return REVISION_PROPERTY.equals( property ) || CHANGELIST_PROPERTY.equals( property )
            || SHA1_PROPERTY.equals( property );
    }

    // TODO This class MUST test for user properties and THEN for system properties
    @Override
    public void overwriteModelProperties( Properties modelProperties, ModelBuildingRequest request )
    {
        if ( request.getSystemProperties().containsKey( REVISION_PROPERTY ) )
        {
            modelProperties.put( REVISION_PROPERTY, request.getSystemProperties().get( REVISION_PROPERTY ) );
        }
        if ( request.getSystemProperties().containsKey( CHANGELIST_PROPERTY ) )
        {
            modelProperties.put( CHANGELIST_PROPERTY, request.getSystemProperties().get( CHANGELIST_PROPERTY ) );
        }
        if ( request.getSystemProperties().containsKey( SHA1_PROPERTY ) )
        {
            modelProperties.put( SHA1_PROPERTY, request.getSystemProperties().get( SHA1_PROPERTY ) );
        }

    }
}
