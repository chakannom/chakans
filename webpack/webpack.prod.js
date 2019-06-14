const webpack = require('webpack');
const { BaseHrefWebpackPlugin } = require('base-href-webpack-plugin');
const webpackMerge = require('webpack-merge');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const OptimizeCSSAssetsPlugin = require("optimize-css-assets-webpack-plugin");
const Visualizer = require('webpack-visualizer-plugin');
const MomentLocalesPlugin = require('moment-locales-webpack-plugin');
const TerserPlugin = require('terser-webpack-plugin');
const WorkboxPlugin = require('workbox-webpack-plugin');
const AngularCompilerPlugin = require('@ngtools/webpack').AngularCompilerPlugin;
const path = require('path');

const utils = require('./utils.js');
const commonIndexAppConfig = require('./webpack.common.index-app.js');
const commonNonIndexAppConfig = require('./webpack.common.non-index-app.js');

const ENV = 'production';
const sass = require('sass');
const IMGPROXY = { url: '#chakans.imgproxy.url#', key: '#chakans.imgproxy.key#', salt: '#chakans.imgproxy.salt#' };

module.exports = [
    // Index
    webpackMerge(commonIndexAppConfig({ env: ENV, imgproxy: IMGPROXY }), {
        // Enable source maps. Please note that this will slow down the build.
        // You have to enable it in Terser config below and in tsconfig-aot.json as well
        // devtool: 'source-map',
        entry: {
            polyfills: './src/main/webapp/index/app/polyfills',
            global: './src/main/webapp/index/content/scss/global.scss',
            main: './src/main/webapp/index/app/app.main'
        },
        output: {
            path: utils.root('build/resources/main/static/'),
            filename: 'index/app/[name].[hash].bundle.js',
            chunkFilename: 'index/app/[id].[hash].chunk.js'
        },
        module: {
            rules: [
                {
                    test: /\.html$/,
                    loader: 'html-loader',
                    options: {
                        minimize: true,
                        caseSensitive: true,
                        removeAttributeQuotes:false,
                        minifyJS:false,
                        minifyCSS:false
                    },
                    exclude: /(src\/main\/webapp\/index.html)/
                },
                {
                    test: /(?:\.ngfactory\.js|\.ngstyle\.js|\.ts)$/,
                    loader: '@ngtools/webpack'
                },
                {
                    test: /\.scss$/,
                    use: ['to-string-loader', 'css-loader', {
                        loader: 'sass-loader',
                        options: { implementation: sass }
                    }],
                    exclude: /(vendor\.scss|global\.scss)/
                },
                {
                    test: /(vendor\.scss|global\.scss)/,
                    use: [
                        {
                            loader: MiniCssExtractPlugin.loader,
                            options: {
                                publicPath: '../'
                            }
                        },
                        'css-loader',
                        'postcss-loader',
                        {
                            loader: 'sass-loader',
                            options: { implementation: sass }
                        }
                    ]
                },
                {
                    test: /\.css$/,
                    use: ['to-string-loader', 'css-loader'],
                    exclude: /(vendor\.css|global\.css)/
                },
                {
                    test: /(vendor\.css|global\.css)/,
                    use: [
                        {
                            loader: MiniCssExtractPlugin.loader,
                            options: {
                                publicPath: '../'
                            }
                        },
                        'css-loader',
                        'postcss-loader'
                    ]
                }
            ]
        },
        optimization: {
            runtimeChunk: false,
            splitChunks: {
                cacheGroups: {
                    commons: {
                        test: /[\\/]node_modules[\\/]/,
                        name: 'vendors',
                        chunks: 'all'
                    }
                }
            },
            minimizer: [
                new TerserPlugin({
                    parallel: true,
                    cache: true,
                    // sourceMap: true, // Enable source maps. Please note that this will slow down the build
                    terserOptions: {
                        ecma: 6,
                        ie8: false,
                        toplevel: true,
                        module: true,
                        compress: {
                            dead_code: true,
                            warnings: false,
                            properties: true,
                            drop_debugger: true,
                            conditionals: true,
                            booleans: true,
                            loops: true,
                            unused: true,
                            toplevel: true,
                            if_return: true,
                            inline: true,
                            join_vars: true,
                            ecma: 6,
                            module: true,
                            toplevel: true
                        },
                        output: {
                            comments: false,
                            beautify: false,
                            indent_level: 2,
                            ecma: 6
                        },
                        mangle: {
                            module: true,
                            toplevel: true
                        }
                    }
                }),
                new OptimizeCSSAssetsPlugin({})
            ]
        },
        plugins: [
            new MiniCssExtractPlugin({
                // Options similar to the same options in webpackOptions.output
                // both options are optional
                filename: 'content/[name].[contenthash].css',
                chunkFilename: 'content/[id].css'
            }),
            new MomentLocalesPlugin({
                localesToKeep: [
                    'ko',
                    'en'
                ]
            }),
            new Visualizer({
                // Webpack statistics in target folder
                filename: '../stats/index.html'
            }),
            new AngularCompilerPlugin({
                mainPath: utils.root('src/main/webapp/index/app/app.main.ts'),
                tsConfigPath: utils.root('tsconfig-aot.json'),
                sourceMap: true
            }),
            new webpack.LoaderOptionsPlugin({
                minimize: true,
                debug: false
            }),
            new WorkboxPlugin.GenerateSW({
              clientsClaim: true,
              skipWaiting: true,
            }),
            new BaseHrefWebpackPlugin({ baseHref: '/' })
        ],
        mode: 'production'
    }),
    // Accounts
    webpackMerge(commonNonIndexAppConfig({ env: ENV, imgproxy: IMGPROXY, appName: 'accounts' }), {
        // Enable source maps. Please note that this will slow down the build.
        // You have to enable it in Terser config below and in tsconfig-aot.json as well
        // devtool: 'source-map',
        entry: {
            polyfills: './src/main/webapp/accounts/app/polyfills',
            global: './src/main/webapp/accounts/content/scss/global.scss',
            main: './src/main/webapp/accounts/app/app.main'
        },
        output: {
            path: utils.root('build/resources/main/static/accounts/'),
            filename: 'app/[name].[hash].bundle.js',
            chunkFilename: 'app/[id].[hash].chunk.js'
        },
        module: {
            rules: [
                {
                    test: /\.html$/,
                    loader: 'html-loader',
                    options: {
                        minimize: true,
                        caseSensitive: true,
                        removeAttributeQuotes:false,
                        minifyJS:false,
                        minifyCSS:false
                    },
                    exclude: /(src\/main\/webapp\/accounts\/index.html)/
                },
                {
                    test: /(?:\.ngfactory\.js|\.ngstyle\.js|\.ts)$/,
                    loader: '@ngtools/webpack'
                },
                {
                    test: /\.scss$/,
                    use: ['to-string-loader', 'css-loader', {
                        loader: 'sass-loader',
                        options: { implementation: sass }
                    }],
                    exclude: /(vendor\.scss|global\.scss)/
                },
                {
                    test: /(vendor\.scss|global\.scss)/,
                    use: [
                        {
                            loader: MiniCssExtractPlugin.loader,
                            options: {
                                publicPath: '../'
                            }
                        },
                        'css-loader',
                        'postcss-loader',
                        {
                            loader: 'sass-loader',
                            options: { implementation: sass }
                        }
                    ]
                },
                {
                    test: /\.css$/,
                    use: ['to-string-loader', 'css-loader'],
                    exclude: /(vendor\.css|global\.css)/
                },
                {
                    test: /(vendor\.css|global\.css)/,
                    use: [
                        {
                            loader: MiniCssExtractPlugin.loader,
                            options: {
                                publicPath: '../'
                            }
                        },
                        'css-loader',
                        'postcss-loader'
                    ]
                }
            ]
        },
        optimization: {
            runtimeChunk: false,
            splitChunks: {
                cacheGroups: {
                    commons: {
                        test: /[\\/]node_modules[\\/]/,
                        name: 'vendors',
                        chunks: 'all'
                    }
                }
            },
            minimizer: [
                new TerserPlugin({
                    parallel: true,
                    cache: true,
                    // sourceMap: true, // Enable source maps. Please note that this will slow down the build
                    terserOptions: {
                        ecma: 6,
                        ie8: false,
                        toplevel: true,
                        module: true,
                        compress: {
                            dead_code: true,
                            warnings: false,
                            properties: true,
                            drop_debugger: true,
                            conditionals: true,
                            booleans: true,
                            loops: true,
                            unused: true,
                            toplevel: true,
                            if_return: true,
                            inline: true,
                            join_vars: true,
                            ecma: 6,
                            module: true,
                            toplevel: true
                        },
                        output: {
                            comments: false,
                            beautify: false,
                            indent_level: 2,
                            ecma: 6
                        },
                        mangle: {
                            module: true,
                            toplevel: true
                        }
                    }
                }),
                new OptimizeCSSAssetsPlugin({})
            ]
        },
        plugins: [
            new MiniCssExtractPlugin({
                // Options similar to the same options in webpackOptions.output
                // both options are optional
                filename: 'content/[name].[contenthash].css',
                chunkFilename: 'content/[id].css'
            }),
            new MomentLocalesPlugin({
                localesToKeep: [
                    'ko',
                    'en'
                ]
            }),
            new Visualizer({
                // Webpack statistics in target folder
                filename: '../../stats/accounts.html'
            }),
            new AngularCompilerPlugin({
                mainPath: utils.root('src/main/webapp/accounts/app/app.main.ts'),
                tsConfigPath: utils.root('tsconfig-aot.json'),
                sourceMap: true
            }),
            new webpack.LoaderOptionsPlugin({
                minimize: true,
                debug: false
            }),
            new WorkboxPlugin.GenerateSW({
              clientsClaim: true,
              skipWaiting: true,
            }),
            new BaseHrefWebpackPlugin({ baseHref: '/accounts/' })
        ],
        mode: 'production'
    }),
    // Blogs
    webpackMerge(commonNonIndexAppConfig({ env: ENV, imgproxy: IMGPROXY, appName: 'blogs' }), {
        // Enable source maps. Please note that this will slow down the build.
        // You have to enable it in Terser config below and in tsconfig-aot.json as well
        // devtool: 'source-map',
        entry: {
            polyfills: './src/main/webapp/blogs/app/polyfills',
            global: './src/main/webapp/blogs/content/scss/global.scss',
            main: './src/main/webapp/blogs/app/app.main'
        },
        output: {
            path: utils.root('build/resources/main/static/blogs/'),
            filename: 'app/[name].[hash].bundle.js',
            chunkFilename: 'app/[id].[hash].chunk.js'
        },
        module: {
            rules: [
                {
                    test: /\.html$/,
                    loader: 'html-loader',
                    options: {
                        minimize: true,
                        caseSensitive: true,
                        removeAttributeQuotes:false,
                        minifyJS:false,
                        minifyCSS:false
                    },
                    exclude: /(src\/main\/webapp\/blogs\/index.html)/
                },
                {
                    test: /(?:\.ngfactory\.js|\.ngstyle\.js|\.ts)$/,
                    loader: '@ngtools/webpack'
                },
                {
                    test: /\.scss$/,
                    use: ['to-string-loader', 'css-loader', {
                        loader: 'sass-loader',
                        options: { implementation: sass }
                    }],
                    exclude: /(vendor\.scss|global\.scss)/
                },
                {
                    test: /(vendor\.scss|global\.scss)/,
                    use: [
                        {
                            loader: MiniCssExtractPlugin.loader,
                            options: {
                                publicPath: '../'
                            }
                        },
                        'css-loader',
                        'postcss-loader',
                        {
                            loader: 'sass-loader',
                            options: { implementation: sass }
                        }
                    ]
                },
                {
                    test: /\.css$/,
                    use: ['to-string-loader', 'css-loader'],
                    exclude: /(vendor\.css|global\.css)/
                },
                {
                    test: /(vendor\.css|global\.css)/,
                    use: [
                        {
                            loader: MiniCssExtractPlugin.loader,
                            options: {
                                publicPath: '../'
                            }
                        },
                        'css-loader',
                        'postcss-loader'
                    ]
                }
            ]
        },
        optimization: {
            runtimeChunk: false,
            splitChunks: {
                cacheGroups: {
                    commons: {
                        test: /[\\/]node_modules[\\/]/,
                        name: 'vendors',
                        chunks: 'all'
                    }
                }
            },
            minimizer: [
                new TerserPlugin({
                    parallel: true,
                    cache: true,
                    // sourceMap: true, // Enable source maps. Please note that this will slow down the build
                    terserOptions: {
                        ecma: 6,
                        ie8: false,
                        toplevel: true,
                        module: true,
                        compress: {
                            dead_code: true,
                            warnings: false,
                            properties: true,
                            drop_debugger: true,
                            conditionals: true,
                            booleans: true,
                            loops: true,
                            unused: true,
                            toplevel: true,
                            if_return: true,
                            inline: true,
                            join_vars: true,
                            ecma: 6,
                            module: true,
                            toplevel: true
                        },
                        output: {
                            comments: false,
                            beautify: false,
                            indent_level: 2,
                            ecma: 6
                        },
                        mangle: {
                            module: true,
                            toplevel: true
                        }
                    }
                }),
                new OptimizeCSSAssetsPlugin({})
            ]
        },
        plugins: [
            new MiniCssExtractPlugin({
                // Options similar to the same options in webpackOptions.output
                // both options are optional
                filename: 'content/[name].[contenthash].css',
                chunkFilename: 'content/[id].css'
            }),
            new MomentLocalesPlugin({
                localesToKeep: [
                    'ko',
                    'en'
                ]
            }),
            new Visualizer({
                // Webpack statistics in target folder
                filename: '../../stats/blogs.html'
            }),
            new AngularCompilerPlugin({
                mainPath: utils.root('src/main/webapp/blogs/app/app.main.ts'),
                tsConfigPath: utils.root('tsconfig-aot.json'),
                sourceMap: true
            }),
            new webpack.LoaderOptionsPlugin({
                minimize: true,
                debug: false
            }),
            new WorkboxPlugin.GenerateSW({
                clientsClaim: true,
                skipWaiting: true,
            }),
            new BaseHrefWebpackPlugin({ baseHref: '/blogs/' })
        ],
        mode: 'production'
    }),
    // Admin
    webpackMerge(commonNonIndexAppConfig({ env: ENV, imgproxy: IMGPROXY, appName: 'admin' }), {
        // Enable source maps. Please note that this will slow down the build.
        // You have to enable it in Terser config below and in tsconfig-aot.json as well
        // devtool: 'source-map',
        entry: {
            polyfills: './src/main/webapp/admin/app/polyfills',
            global: './src/main/webapp/admin/content/scss/global.scss',
            main: './src/main/webapp/admin/app/app.main'
        },
        output: {
            path: utils.root('build/resources/main/static/admin/'),
            filename: 'app/[name].[hash].bundle.js',
            chunkFilename: 'app/[id].[hash].chunk.js'
        },
        module: {
            rules: [
                {
                    test: /\.html$/,
                    loader: 'html-loader',
                    options: {
                        minimize: true,
                        caseSensitive: true,
                        removeAttributeQuotes:false,
                        minifyJS:false,
                        minifyCSS:false
                    },
                    exclude: /(src\/main\/webapp\/admin\/index.html)/
                },
                {
                    test: /(?:\.ngfactory\.js|\.ngstyle\.js|\.ts)$/,
                    loader: '@ngtools/webpack'
                },
                {
                    test: /\.scss$/,
                    use: ['to-string-loader', 'css-loader', {
                        loader: 'sass-loader',
                        options: { implementation: sass }
                    }],
                    exclude: /(vendor\.scss|global\.scss)/
                },
                {
                    test: /(vendor\.scss|global\.scss)/,
                    use: [
                        {
                            loader: MiniCssExtractPlugin.loader,
                            options: {
                                publicPath: '../'
                            }
                        },
                        'css-loader',
                        'postcss-loader',
                        {
                            loader: 'sass-loader',
                            options: { implementation: sass }
                        }
                    ]
                },
                {
                    test: /\.css$/,
                    use: ['to-string-loader', 'css-loader'],
                    exclude: /(vendor\.css|global\.css)/
                },
                {
                    test: /(vendor\.css|global\.css)/,
                    use: [
                        {
                            loader: MiniCssExtractPlugin.loader,
                            options: {
                                publicPath: '../'
                            }
                        },
                        'css-loader',
                        'postcss-loader'
                    ]
                }
            ]
        },
        optimization: {
            runtimeChunk: false,
            splitChunks: {
                cacheGroups: {
                    commons: {
                        test: /[\\/]node_modules[\\/]/,
                        name: 'vendors',
                        chunks: 'all'
                    }
                }
            },
            minimizer: [
                new TerserPlugin({
                    parallel: true,
                    cache: true,
                    // sourceMap: true, // Enable source maps. Please note that this will slow down the build
                    terserOptions: {
                        ecma: 6,
                        ie8: false,
                        toplevel: true,
                        module: true,
                        compress: {
                            dead_code: true,
                            warnings: false,
                            properties: true,
                            drop_debugger: true,
                            conditionals: true,
                            booleans: true,
                            loops: true,
                            unused: true,
                            toplevel: true,
                            if_return: true,
                            inline: true,
                            join_vars: true,
                            ecma: 6,
                            module: true,
                            toplevel: true
                        },
                        output: {
                            comments: false,
                            beautify: false,
                            indent_level: 2,
                            ecma: 6
                        },
                        mangle: {
                            module: true,
                            toplevel: true
                        }
                    }
                }),
                new OptimizeCSSAssetsPlugin({})
            ]
        },
        plugins: [
            new MiniCssExtractPlugin({
                // Options similar to the same options in webpackOptions.output
                // both options are optional
                filename: 'content/[name].[contenthash].css',
                chunkFilename: 'content/[id].css'
            }),
            new MomentLocalesPlugin({
                localesToKeep: [
                    'ko',
                    'en'
                ]
            }),
            new Visualizer({
                // Webpack statistics in target folder
                filename: '../../stats/admin.html'
            }),
            new AngularCompilerPlugin({
                mainPath: utils.root('src/main/webapp/admin/app/app.main.ts'),
                tsConfigPath: utils.root('tsconfig-aot.json'),
                sourceMap: true
            }),
            new webpack.LoaderOptionsPlugin({
                minimize: true,
                debug: false
            }),
            new WorkboxPlugin.GenerateSW({
              clientsClaim: true,
              skipWaiting: true,
            }),
            new BaseHrefWebpackPlugin({ baseHref: '/admin/' })
        ],
        mode: 'production'
    })
];
