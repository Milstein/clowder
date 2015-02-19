package models

import java.util.Date
import api.{WithPermission, Permission}
import securesocial.core.Identity
import api.WithPermission


/**
 * Uploaded files.
 *
 * @author Luigi Marini
 *
 */
case class File(
  id: UUID = UUID.generate,
  path: Option[String] = None,
  filename: String,
  author: Identity,
  uploadDate: Date,
  contentType: String,
  length: Long = 0,
  showPreviews: String = "DatasetLevel",
  sections: List[Section] = List.empty,
  previews: List[Preview] = List.empty,
  tags: List[Tag] = List.empty,
  thumbnail_id: Option[String] = None,
  jsonldMetadata : List[FileMetadata] = List.empty,
  @deprecated("use FileMetadata","since the use of jsonld") metadata: List[Map[String, Any]] = List.empty,
  @deprecated("will not be used in the future","since the use of jsonld") isIntermediate: Option[Boolean] = None,
  @deprecated("use FileMetadata","since the use of jsonld") userMetadata: Map[String, Any] = Map.empty,
  @deprecated("use FileMetadata","since the use of jsonld") xmlMetadata: Map[String, Any] = Map.empty,
  @deprecated("use FileMetadata","since the use of jsonld") userMetadataWasModified: Option[Boolean] = None,
  licenseData: LicenseData = new LicenseData(),
  notesHTML: Option[String] = None ) {
    
  /**
   * Utility method to check a given file and a given identity for permissions from the license 
   * to allow the raw bytes to be downloaded. 
   * 
   * @param anIdentity An Option, possibly containing the securesocial information for a user
   * 
   * @return A boolean, true if the license allows the bytes to be downloaded, false otherwise
   *   
   */
  def checkLicenseForDownload(anIdentity: Option[Identity]): Boolean = {
    licenseData.isDownloadAllowed || (anIdentity match {
      case Some(x) => WithPermission(Permission.DownloadFiles).isAuthorized(x) || licenseData.isRightsOwner(x.fullName)
      case None => false
    })
  }
}

  
case class Versus(
  fileId: UUID,
  descriptors: Map[String,Any]= Map.empty
)
