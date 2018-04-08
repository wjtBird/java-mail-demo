//package com.sinolife.mailbusiness.utils;
//
//import com.artofsolving.jodconverter.DocumentConverter;
//import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
//import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException;
//import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
//import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.File;
//import java.net.ConnectException;
//
//
///**
// * office格式转换成pdf
// */
//public class DocConverter {
//
//	/**
//	 * 日志
//	 */
//	private Logger LOGGER = LoggerFactory.getLogger(DocConverter.class);
//
//	private static final int environment = 1;// 环境 1：Windows
//	private String fileString;
//	private String fileName; // 文件名称
//	private File pdfFile; // pdf文件路径
//	private File docFile; // office文件路径
//	private File htmlFile; // html文件的路径
//
//	public DocConverter(String fileString) {
//		ini(fileString);
//		LOGGER.info("文件路径： "+fileString);
//	}
//
//	/**
//	 * * 重新设置file
//	 *
//	 * @param fileString
//	 *
//	 */
//	public void setFile(String fileString) {
//		ini(fileString);
//	}
//
//	/**
//	 *  * 初始化
//	 *
//	 * @param fileString
//	 *
//	 */
//	private void ini(String fileString) {
//		this.fileString = fileString;
//		fileName = fileString.substring(0, fileString.lastIndexOf("."));
//		docFile = new File(fileString);
//		pdfFile = new File(fileName+ ".pdf");
//		htmlFile = new File(fileName+ ".html");
//	}
//
//	/**
//	 *  转为PDF
//	 *
//	 */
//	private void doc2pdf() throws Exception {
//		if (docFile.exists()) {
//			if (!pdfFile.exists()) {
//				OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
//				try {
//					connection.connect();
//					DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
//					converter.convert(docFile, pdfFile);
//					connection.disconnect();
//					LOGGER.info("****pdf转换成功，PDF输出： "+ pdfFile.getPath() + "****");
//				} catch (java.net.ConnectException e) {
//					e.printStackTrace();
//					LOGGER.info("****pdf转换器异常，openoffice 服务未启动！****");
//					throw e;
//				} catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
//					e.printStackTrace();
//					LOGGER.info("****pdf转换器异常，读取转换文件 失败****");
//					throw e;
//				} catch (Exception e) {
//					e.printStackTrace();
//					throw e;
//				}
//			} else {
//				LOGGER.info("****已经转换为pdf，不需要再进行转化 ****");
//			}
//		} else {
//			LOGGER.info("****pdf转换器异常，需要转换的文档不存在， 无法转换****");
//		}
//	}
//
//	/**
//	 *  office文件转为html文件
//	 */
//	private void officeToHtml() throws Exception {
//		if (docFile.exists()) {
//			if (!htmlFile.exists()) {
//				OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
//				try {
//					connection.connect();
//					DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
//					converter.convert(docFile, htmlFile);
//					connection.disconnect();
//					LOGGER.info("html转换成功，html输出文件地址： "+ htmlFile.getPath());
//				} catch (ConnectException e) {
//					e.printStackTrace();
//					LOGGER.info("html转换器异常，openoffice 服务未启动！");
//					throw e;
//				} catch (OpenOfficeException e) {
//					e.printStackTrace();
//					LOGGER.info("html转换器异常，读取转换文件 失败");
//					throw e;
//				} catch (Exception e) {
//					e.printStackTrace();
//					throw e;
//				}
//			} else {
//				LOGGER.info("已经转换为html文件，不需要再进行转化");
//			}
//		} else {
//			LOGGER.info("html转换器异常，需要转换的文档不存在， 无法转换");
//		}
//	}
//
//	/**
//	 * * 转换主方法
//	 */
//	@SuppressWarnings("unused")
//	public void conver() {
//		try {
//			officeToHtml();
//		} catch (Exception e) {
//			  e.printStackTrace();
//		}
//	}
//}
//
